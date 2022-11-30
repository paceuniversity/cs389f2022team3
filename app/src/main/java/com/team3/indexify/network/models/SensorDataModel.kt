package com.team3.indexify.network.models

data class SensorDataModel(
    val deployment_id: Int,
    val measurement: String,
    val sensors: List<SensorModel>,
    val timestamp: String
)