package com.example.litmuscloudclonecoding

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.litmuscloudclonecoding.DataStoreObject.dataStore
import com.example.litmuscloudclonecoding.LoginData.Key
import com.example.litmuscloudclonecoding.databinding.ActivityLoginBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private var litmusId = ""
    private var litmusPassword = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loginButtonClicked()

    }

    private fun loginButtonClicked() {
        binding.loginButton.setOnClickListener {
            litmusId = binding.idEditText.text.toString()
            litmusPassword = binding.passWordEditText.text.toString()
            if (litmusId.length == 0) {
                Toast.makeText(this@LoginActivity, "id를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            } else if (litmusPassword.length == 0) {
                Toast.makeText(this@LoginActivity, "password를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            } else {

                val loginFoId = LoginInformation(litmusId,litmusPassword)

                RetrofitObjects.litmusCloud.getKey(loginFoId).enqueue(object : Callback<Key> {
                    override fun onResponse(call: Call<Key>, response: Response<Key>) {
                        if (response.isSuccessful) {
                            val main = response.body()
                            val key = main?.key.toString()
                            Log.d("asdf key","${key}")

                            lifecycleScope.launch {
                                saveToekn("litmusToken",key)
                            }


                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()

                        } else {
                            Toast.makeText(this@LoginActivity, "로그인할 수 없습니다. id와 pw를 확인해 주세요.", Toast.LENGTH_SHORT).show()
                            Log.d("asdf 로그인 실패","Unable to log in with provided credentials")
                        }
                    }

                    override fun onFailure(call: Call<Key>, t: Throwable) {
                        Log.d("asdf onFailure","onFailure")
                        Toast.makeText(this@LoginActivity, "로그인할 수 없습니다. 나중에 다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
                    }

                })

            }


        }
    }

    suspend fun saveToekn(key : String, value : String) {
        val dataStoreKey = stringPreferencesKey(key)
        dataStore.edit { settings ->
            settings[dataStoreKey] = value
        }
    }

    suspend fun readToekn(key : String) : String? {
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = dataStore.data.first()

        return preferences[dataStoreKey]
    }

}




































