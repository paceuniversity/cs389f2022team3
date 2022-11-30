package com.team3.indexify.network.models

data class SensorModel(
    val name: String,
    val value: Double
) {
    fun getValue(): String {
        return value.toString()
    }
}