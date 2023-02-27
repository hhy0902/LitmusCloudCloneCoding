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
import com.example.litmuscloudclonecoding.DataStoreObject.dataStore
import com.example.litmuscloudclonecoding.LoginData.Key
import com.example.litmuscloudclonecoding.OrganizationData.Org
import com.example.litmuscloudclonecoding.OrganizationData.OrgItem
import com.example.litmuscloudclonecoding.Site.Site
import com.example.litmuscloudclonecoding.Zone.Zone
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

//    private fun getZone() {
//        RetrofitObjects.litmusCloud.getZone("Token ${litmusToken}", siteId)
//            .enqueue(object : Callback<Zone> {
//                override fun onResponse(call: Call<Zone>, response: Response<Zone>) {
//                    if (response.isSuccessful) {
//                        val main = response.body()
//                        val zone = main
//                    }
//                }
//
//                override fun onFailure(call: Call<Zone>, t: Throwable) {
//                    Log.d("asdf zone onFailure","onFailure ${t.message}")
//                }
//
//            })
//    }

    suspend fun readToekn(key : String) : String? {
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = dataStore.data.first()

        return preferences[dataStoreKey]
    }

}





































