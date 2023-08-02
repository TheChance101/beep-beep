package org.thechance.service_taxi.domain.entity

data class Trip(
    val id: String,
    val driverId: String? = null,
    val clientId: String? = null,
    val from: String? = null,
    val to: String? = null,
    val rate: Double? = null,
    val price: Double? = null,
    val isDeleted: Boolean? = null,
)

/*
curd
driver/history/
user/{id}/history/
 */