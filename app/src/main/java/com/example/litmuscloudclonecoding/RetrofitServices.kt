package com.example.litmuscloudclonecoding

import com.example.litmuscloudclonecoding.LoginData.Key
import com.example.litmuscloudclonecoding.OrganizationData.Org
import com.example.litmuscloudclonecoding.OrganizationData.OrgItem
import com.example.litmuscloudclonecoding.Site.Site
import com.example.litmuscloudclonecoding.Zone.Zone
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitServices {

    @POST("auth/login/")
    fun getKey(
        @Body loginInformation: LoginInformation
    ) : Call<Key>

    @GET("v1/users/self/orgs/")
    fun getOrganization(
        @Header("AUTHORIZATION") AUTHORIZATION : String
    ) : Call<Org>

    @GET("/v1/orgs/{org_id}/sites/")
    fun getSite(
        @Header("AUTHORIZATION") AUTHORIZATION : String,
        @Path("org_id") org_id : Int
    ) : Call<Site>

    @GET("/v1/sites/{site_id}/zones/")
    fun getZone(
        @Header("AUTHORIZATION") AUTHORIZATION : String,
        @Path("site_id") site_id : Int
    ) : Call<Zone>

}










































