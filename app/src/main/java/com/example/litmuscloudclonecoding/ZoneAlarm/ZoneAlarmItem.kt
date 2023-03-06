package com.example.litmuscloudclonecoding.ZoneAlarm

data class ZoneAlarmItem(
    val alarmName: Any,
    val duration: Any,
    val finishedAt: Any,
    val insertedAt: String,
    val nodeId: String,
    val nodeName: String,
    val sensorType: SensorType,
    val severity: Severity,
    val startedAt: String,
    val value: Any,
    val zoneId: Int,
    val zoneName: String
)