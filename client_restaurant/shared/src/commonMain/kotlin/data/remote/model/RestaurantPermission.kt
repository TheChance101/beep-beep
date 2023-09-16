package data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RestaurantPermission(
    @SerialName("aud")
    val audience: String? = "",
    @SerialName("iss")
    val issuer: String? = "",
    @SerialName("permission")
    val permission: String? = "",
    @SerialName("exp")
    val expiration: Long? = 0,
    @SerialName("tokenType")
    val tokenType: String? = "",
    @SerialName("userId")
    val userId: String? = "",
    @SerialName("username")
    val username: String? = ""
)
