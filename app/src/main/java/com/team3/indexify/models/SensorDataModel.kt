package com.team3.indexify.models


data class SensorDataModel(
    val deployment_id: Int,
    val measurement: String,
    val sensors: SensorModel,
    val timestamp: String
)