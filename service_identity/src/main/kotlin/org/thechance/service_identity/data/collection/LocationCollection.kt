package org.thechance.service_identity.data.collection

import kotlinx.serialization.Serializable

@Serializable
data class LocationCollection(
    val latitude: Double,
    val longitude: Double,
)
