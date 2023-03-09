package com.example.litmuscloudclonecoding.Battery

data class BatteryItem(
    val co2: Boolean,
    val diag_time: String,
    val lte: Boolean,
    val node_id: Int,
    val node_name: String,
    val node_uid: String,
    val site_id: Int,
    val site_name: String,
    val voltage: Double,
    val zone_id: Int,
    val zone_name: String
)