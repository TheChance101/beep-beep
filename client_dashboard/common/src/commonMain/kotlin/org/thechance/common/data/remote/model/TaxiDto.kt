package org.thechance.common.data.remote.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class TaxiDto(
    @SerializedName("id")
    val id: String?,
    @SerializedName("plate_number")
    val plateNumber: String?,
    @SerializedName("color")
    val color: Int?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("seats")
    val seats: Int?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("trips")
    val trips: String?,
)