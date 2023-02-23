package com.example.litmuscloudclonecoding

import com.example.litmuscloudclonecoding.LoginData.Key
import com.example.litmuscloudclonecoding.OrganizationData.Org
import com.example.litmuscloudclonecoding.OrganizationData.OrgItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface RetrofitServices {

    @POST("auth/login/")
    fun getKey(
        @Body loginInformation: LoginInformation
    ) : Call<Key>

    @GET("v1/users/self/orgs/")
    fun getOrganization(
        @Header("AUTHORIZATION") AUTHORIZATION : String
    ) : Call<Org>

}










































