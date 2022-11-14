package com.team3.indexify.models

data class SensorData(
    val deployment_id: Int,
    val measurement: String,
    val sensors: Sensors,
    val timestamp: String
) {
    data class Sensors(
        val Cond: Double,
        val DOpct: Double,
        val Sal: Double,
        val Temp: Double,
        val Turb: Double,
        val lat: Double,
        val lon: Double,
        val pH: Double
    )
}