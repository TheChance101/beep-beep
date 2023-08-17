package org.thechance.service_taxi.data.collection

import kotlinx.serialization.Serializable

@Serializable
data class LocationCollection(
    val latitude: Double,
    val longitude: Double,
)