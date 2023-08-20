package org.thechance.common.data.remote.model


data class TaxiDto(
    val id: String,
    val plateNumber:String,
    val color: Int,
    val type: String,
    val seats: Int,
    val username: String,
    val status: Int,
    val trips: String,
)