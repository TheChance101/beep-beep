package org.thechance.common.data.gateway.remote.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("items") val users: List<UserDto>,
    @SerializedName("total") val total: Int,
)

data class UserDto(
    @SerializedName("id") val id: String,
    @SerializedName("fullName") val fullName: String,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("permission") val permission: Int,
    @SerializedName("image_url") val imageUrl: String? = null,
    @SerializedName("country") val country: String = "",
)
