package org.thechance.service_identity.data.collection

import kotlinx.serialization.Serializable

@Serializable
data class LocationCollection(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)
