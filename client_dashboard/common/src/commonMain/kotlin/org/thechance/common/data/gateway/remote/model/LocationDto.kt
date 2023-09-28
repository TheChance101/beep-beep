package org.thechance.common.data.gateway.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationInfoDto(
    @SerialName("country") var country: String? = null,
    @SerialName("countryCode") var countryCode: String? = null,
    @SerialName("region") var region: String? = null,
    @SerialName("regionName") var regionName: String? = null,
    @SerialName("city") var city: String? = null,
    @SerialName("zip") var zip: String? = null,
    @SerialName("lat") var lat: Double? = null,
    @SerialName("lon") var lon: Double? = null,
)