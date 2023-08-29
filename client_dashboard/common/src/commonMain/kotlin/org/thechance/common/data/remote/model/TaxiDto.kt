package org.thechance.common.data.remote.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class TaxiDto(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("plate_number")
    val plateNumber: String? = null,
    @SerializedName("color")
    val color: Int? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("seats")
    val seats: Int? = null,
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("status")
    val status: Int? = null,
    @SerializedName("trips")
    val trips: String? = null,
)