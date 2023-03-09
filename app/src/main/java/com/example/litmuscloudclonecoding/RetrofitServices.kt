package com.example.litmuscloudclonecoding

import com.example.litmuscloudclonecoding.Alarm.Alarm
import com.example.litmuscloudclonecoding.Battery.Battery
import com.example.litmuscloudclonecoding.LoginData.Key
import com.example.litmuscloudclonecoding.OrganizationData.Org
import com.example.litmuscloudclonecoding.OrganizationData.OrgItem
import com.example.litmuscloudclonecoding.Sensor.Sensor
import com.example.litmuscloudclonecoding.Site.Site
import com.example.litmuscloudclonecoding.Zone.Zone
import com.example.litmuscloudclonecoding.ZoneAlarm.ZoneAlarm
import com.google.common.net.HttpHeaders.AUTHORIZATION
import org.checkerframework.checker.units.qual.A
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

    @GET("/v1/orgs/{org_id}/nodes/")
    fun getSensor(
        @Header("AUTHORIZATION") AUTHORIZATION : String,
        @Path("org_id") org_id : Int
    ) : Call<Sensor>

    @GET("/v1/zones/{zone_id}/alarmEvents/?dateFrom=-24h")
    fun getZoneAlarm(
        @Header("AUTHORIZATION") AUTHORIZATION : String,
        @Path("zone_id") zone_id : Int
    ) : Call<ZoneAlarm>

//    @GET("/v1/orgs/{org_id}/nodes/")
//    fun getAlarm(
//        @Header("AUTHORIZATION") AUTHORIZATION : String,
//        @Path("org_id") org_id : Int
//    ) : Call<Alarm>

    @GET("/v1/voltages")
    fun getBattery(
        @Header("AUTHORIZATION") AUTHORIZATION : String,
    ) : Call<Battery>

}










































