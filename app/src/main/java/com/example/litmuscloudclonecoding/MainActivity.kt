package com.example.litmuscloudclonecoding

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.litmuscloudclonecoding.Battery.Battery
import com.example.litmuscloudclonecoding.DataStoreObject.dataStore
import com.example.litmuscloudclonecoding.LoginData.Key
import com.example.litmuscloudclonecoding.OrganizationData.Org
import com.example.litmuscloudclonecoding.OrganizationData.OrgItem
import com.example.litmuscloudclonecoding.Sensor.Sensor
import com.example.litmuscloudclonecoding.Site.Site
import com.example.litmuscloudclonecoding.Zone.Zone
import com.example.litmuscloudclonecoding.ZoneAlarm.ZoneAlarm
import com.example.litmuscloudclonecoding.ZoneAlarm.ZoneAlarmItem
import com.example.litmuscloudclonecoding.databinding.ActivityMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    var organizationId = 0
    var litmusToken = ""
    var alarmList = mutableListOf<Any>()
    val db = Firebase.firestore



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val getToken = db.collection("token").document("token")
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("asdf document token","${document.data?.values?.firstOrNull()}")

                    litmusToken = document.data?.values?.firstOrNull().toString()

                    RetrofitObjects.litmusCloud
                        .getOrganization("Token ${litmusToken}")
                        .enqueue(object : Callback<Org> {
                            override fun onResponse(call: Call<Org>, response: Response<Org>) {
                                if (response.isSuccessful) {
                                    val main = response.body()
                                    organizationId = main?.firstOrNull()?.id!!

                                    getSite()
                                    getSensor()
                                    getBattery()

                                    Log.d("asdf organizationId","${organizationId}")
                                } else {
                                    Log.d("asdf organizationId","실패")
                                }
                            }

                            override fun onFailure(call: Call<Org>, t: Throwable) {
                                Log.d("asdf onFailure","onFailure ${t.message}")
                            }

                        })

                }
            }

        binding.button.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.siteText.setOnClickListener {

        }

//        lifecycleScope.launch {
//            litmusToken = readToekn("litmusToken").toString()
//            Log.d("asdf litmusToken","${litmusToken}")
//        }

    }

    private fun getSite() {
        RetrofitObjects.litmusCloud
            .getSite("Token ${litmusToken}", organizationId)
            .enqueue(object : Callback<Site> {
                override fun onResponse(call: Call<Site>, response: Response<Site>) {
                    if (response.isSuccessful) {
                        val main = response.body()

//                        siteId = main?.firstOrNull()?.id!!
//                        Log.d("asdf siteId","${siteId}")
                        Log.d("asdf siteSize","${main?.size}")
                        binding.siteText.text = "사이트 ${main?.size}"
                        Log.d("asdf siteMain","${main}")

                    } else {
                        Log.d("asdf siteId","실패")
                    }
                }

                override fun onFailure(call: Call<Site>, t: Throwable) {
                    Log.d("asdf site onFailure","onFailure ${t.message}")
                }

            })
    }

    private fun getSensor() {
        RetrofitObjects.litmusCloud.getSensor("Token ${litmusToken}", organizationId)
            .enqueue(object : Callback<Sensor> {
                override fun onResponse(call: Call<Sensor>, response: Response<Sensor>) {
                    if (response.isSuccessful) {
                        val main = response.body()

                        var zoneSize = mutableListOf<Any>()
                        var zoneNumber = mutableListOf<Any>()
                        val sensorSize = main?.size
                        var alarmValue = 0

                        for (i in 0 until sensorSize!!) {
                            zoneSize.add(main.get(i).zoneName)
                            zoneNumber.add(main.get(i).zoneId)
                        }

                        zoneSize = zoneSize.distinct().toMutableList()
                        zoneNumber = zoneNumber.distinct().toMutableList()

                        Log.d("asdf sensormain","${main}")
                        Log.d("asdf sensorSize","${sensorSize}")
                        Log.d("asdf zoneSize","${zoneSize}")
                        Log.d("asdf zoneNumber","${zoneNumber}")

                        for (i in 0..sensorSize-1) {
                            if (main.get(i).status == "alarmed") {
                                alarmValue +=1
                            }
                        }

                        Log.d("asdf alarmValue","${alarmValue}")
                        binding.zoneText.text = "존 ${zoneSize.size}"
                        binding.sensorText.text = "센서 ${sensorSize}"

                        for (i in 0..zoneNumber.size-1) {
                            getZoneAlarmed(zoneNumber.get(i) as Int)
                        }

                    } else {
                        Log.d("asdf sensor 데이터 가져오기 실패","onFailure ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<Sensor>, t: Throwable) {
                    Log.d("asdf sensor onFailure","onFailure ${t.message}")
                }

            })
    }

    private fun getZoneAlarmed(zone_id : Int) {
        RetrofitObjects.litmusCloud.getZoneAlarm("Token ${litmusToken}", zone_id)
            .enqueue(object : Callback<ZoneAlarm> {
                override fun onResponse(call: Call<ZoneAlarm>, response: Response<ZoneAlarm>) {
                    if (response.isSuccessful) {
                        val main = response.body()

                        main?.forEach {
                            alarmList.add(it.severity.name)
                        }

                        Log.d("asdf zoneAlarm main","${main}")
                        Log.d("asdf zoneAlarm alarm","${alarmList}")

                        val danger = alarmList.count {
                            it.equals("위험")
                        }
                        val warning = alarmList.count {
                            it.equals("경고")
                        }
                        val caution = alarmList.count {
                            it.equals("주의")
                        }

                        binding.dangerText.text = "위험 : ${danger}"



                    } else {
                        Log.d("asdf zone alarm 데이터 가져오기 실패","onFailure ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<ZoneAlarm>, t: Throwable) {
                    Log.d("asdf zoneAlarm onFailure","onFailure ${t.message}")
                }

            })
    }

    private fun getBattery() {
        RetrofitObjects.litmusCloud.getBattery("Token ${litmusToken}")
            .enqueue(object : Callback<Battery> {
                override fun onResponse(call: Call<Battery>, response: Response<Battery>) {
                    if (response.isSuccessful) {
                        val main = response.body()

                        Log.d("asdf Battery","${main}")

                    } else {
                        Log.d("asdf Battery 데이터 가져오기 실패","onFailure ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<Battery>, t: Throwable) {
                    Log.d("asdf Battery onFailure","onFailure ${t.message}")
                }

            })
    }


//    suspend fun readToekn(key : String) : String? {
//        val dataStoreKey = stringPreferencesKey(key)
//        val preferences = dataStore.data.first()
//
//        return preferences[dataStoreKey]
//    }

}





































