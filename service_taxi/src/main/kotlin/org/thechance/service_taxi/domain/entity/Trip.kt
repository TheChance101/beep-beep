package org.thechance.service_taxi.domain.entity

data class Trip(
    val id: String,
    val driverId: String? = null,
    val clientId: String? = null,
    val from: Pair<Long, Long>? = null,
    val to: Pair<Long, Long>? = null,
    val rate: Double? = null,
    val price: Double? = null,
    val isDeleted: Boolean? = null,
)
