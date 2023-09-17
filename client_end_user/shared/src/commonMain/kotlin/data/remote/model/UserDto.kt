package data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("email")
    val email: String? = null,
    @SerialName("fullName")
    val fullName: String? = null,
    @SerialName("id")
    val id: String? = null,
    @SerialName("permission")
    val permission: Int? = null,
    @SerialName("username")
    val username: String? = null
)