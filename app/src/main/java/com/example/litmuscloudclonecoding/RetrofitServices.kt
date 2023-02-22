package com.example.litmuscloudclonecoding

import com.example.litmuscloudclonecoding.LoginData.Key
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitServices {

    @POST("auth/login/")
    fun getKey(
        @Body loginInformation: LoginInformation
    ) : Call<Key>

}










































