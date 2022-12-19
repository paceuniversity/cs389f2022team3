package com.team3.indexify.models

data class DeploymentModel(
    val Active: Boolean,
    val DeploymentID: Int,
    val Description: String,
    val EndDate: String,
    val Location: String,
    val Name: String,
    val Resolution: Int,
    val StartDate: String,
    val StationID: Int
) {
    override fun toString(): String = Name
}