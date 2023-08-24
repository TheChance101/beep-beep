package org.thechance.common.data.remote.model


data class TaxiDto(
    val id: String? = null,
    val plateNumber: String,
    val color: Int,
    val type: String,
    val seats: Int,
    val username: String,
    val status: Int? = null,
    val trips: String? = null,
)