package org.thechance.common.data.remote.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import org.thechance.common.domain.entity.User

@Serializable
data class UserDto(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("full_name")
    val fullName: String? = null,
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("country")
    val country: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("permissions")
    val permissions: List<PermissionDto?>? = null
) {
    @Serializable
    data class PermissionDto(
        @SerializedName("id")
        val id: Int? = null,
        @SerializedName("permission")
        val permission: String? = null
    )
}

fun UserDto.toEntity() = User(
    id = id ?: "",
    fullName = fullName ?: "",
    country = country ?: "",
    username = username ?: "",
    email = email ?: "",
    permission = permissions?.map { enumValueOf(it?.permission ?: "") } ?: emptyList(),
)

fun List<UserDto>.toEntity() = map(UserDto::toEntity)