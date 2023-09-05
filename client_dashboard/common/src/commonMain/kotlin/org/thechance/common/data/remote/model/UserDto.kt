package org.thechance.common.data.remote.model

import kotlinx.serialization.SerialName

data class UserDto(
    @SerialName("id") val id: String,
    @SerialName("fullName") val fullName: String,
    @SerialName("username") val username: String,
    @SerialName("email") val email: String,
    @SerialName("permission") val permissions: Int,
    @SerialName("image_url") val imageUrl: String = "",
    @SerialName("country") val country: String = "",
)
