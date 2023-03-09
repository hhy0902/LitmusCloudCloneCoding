package com.example.litmuscloudclonecoding.Alarm

data class AlarmItem(
    val RSRP: Any,
    val activeAt: String,
    val currentMeasures: List<CurrentMeasure>,
    val gps: Boolean,
    val id: String,
    val name: String,
    val `open`: Boolean,
    val routing: Boolean,
    val siteId: Int,
    val siteName: String,
    val status: String,
    val version: String,
    val x: Double,
    val y: Double,
    val zoneId: Int,
    val zoneName: String
)