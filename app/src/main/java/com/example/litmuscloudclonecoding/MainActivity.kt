package com.example.litmuscloudclonecoding

import android.content.Context
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
import com.example.litmuscloudclonecoding.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    var organizationId = ""
    var siteId = ""
    var zoneId = ""
    var litmusToken = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        lifecycleScope.launch {
            litmusToken = readToekn("litmusToken").toString()
            Log.d("asdf litmusToken","${litmusToken}")

            RetrofitObjects.litmusCloud
                .getOrganization("Token ${litmusToken}")
                .enqueue(object : Callback<Org> {
                    override fun onResponse(call: Call<Org>, response: Response<Org>) {
                        if (response.isSuccessful) {
                            val main = response.body()
                            val id = main?.firstOrNull()

                            Log.d("asdf organizationId","${id?.id}")
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

    suspend fun readToekn(key : String) : String? {
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = dataStore.data.first()

        return preferences[dataStoreKey]
    }

}





































