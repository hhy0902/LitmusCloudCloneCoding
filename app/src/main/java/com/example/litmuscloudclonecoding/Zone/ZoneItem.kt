package com.example.litmuscloudclonecoding.Zone

data class ZoneItem(
    val abnormalNodes: Int,
    val batteryCriticalCount: Int,
    val batteryWarningCount: Int,
    val co2CautionCount: Int,
    val co2CriticalCount: Int,
    val co2WarningCount: Int,
    val currentHumidity: Double,
    val currentTemperature: Double,
    val floor_map: String,
    val humidityCautionCount: Int,
    val humidityCriticalCount: Int,
    val humidityWarningCount: Int,
    val id: Int,
    val inactiveNodes: Int,
    val map_height: Int,
    val map_width: Int,
    val name: String,
    val siteId: Int,
    val status: String,
    val temperatureCautionCount: Int,
    val temperatureCriticalCount: Int,
    val temperatureWarningCount: Int,
    val totalNodes: Int,
    val updatedAt: String
)