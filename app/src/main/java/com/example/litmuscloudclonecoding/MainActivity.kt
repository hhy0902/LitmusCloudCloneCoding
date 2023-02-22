package com.example.litmuscloudclonecoding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.litmuscloudclonecoding.LoginData.Key
import com.example.litmuscloudclonecoding.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var litmusId = ""
    private var litmusPassword = ""
    private var litmusKey = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        binding.loginButton.setOnClickListener {
            litmusId = binding.idEditText.text.toString()
            litmusPassword = binding.passWordEditText.text.toString()

            val loginFoId = LoginInformation()



            Log.d("asdf","${loginFoId}")
            Log.d("asdf","${litmusPassword}")

            RetrofitObjects.litmusCloud.getKey(loginFoId).enqueue(object : Callback<Key> {
                override fun onResponse(call: Call<Key>, response: Response<Key>) {
                    if (response.isSuccessful) {
                        val main = response.body()
                        val key = main?.key.toString()
                        litmusKey = key
                        Log.d("asdf key","${litmusKey}")
                        Toast.makeText(this@MainActivity, "qwer ${litmusKey}", Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<Key>, t: Throwable) {
                    Log.d("asdf onFailure","onFailure")
                }

            })
        }
    }



}





































