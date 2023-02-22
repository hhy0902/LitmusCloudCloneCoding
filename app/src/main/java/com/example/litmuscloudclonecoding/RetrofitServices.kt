package com.example.litmuscloudclonecoding

import com.example.litmuscloudclonecoding.LoginData.Key
import retrofit2.Call
import retrofit2.http.*

interface RetrofitServices {

    @Headers("accept: application/json", "content-type: application/json")
    @FormUrlEncoded
    @POST("auth/login/")
    fun getKey(
        @Field("username") username : String,
        @Field("password") password : String,
    ) : Call<Key>

}










































