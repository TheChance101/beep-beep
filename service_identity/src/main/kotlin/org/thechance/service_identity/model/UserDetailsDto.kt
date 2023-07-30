package org.thechance.service_identity.model

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.thechance.service_identity.entity.UserDetails

@Serializable
data class UserDetailsDto(
    @BsonId
    val id: Id<UserDetailsDto>? = null,
    val userId: Id<UserDto>,
    val password: String,
    val email: String,
    val addresses: List<AddressDto>,
    val permissions: List<PermissionDto>
)

fun UserDetailsDto.toUserDetails(): UserDetails {
    return UserDetails(
        id = id.toString(),
        userId = userId.toString(),
        password = password,
        email = email,
        addresses = addresses.map { it.toAddress() },
        permissions = permissions.map { it.toPermission() }
    )

}
