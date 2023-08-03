package org.thechance.service_identity.domain.entity

import org.bson.types.ObjectId
import org.thechance.service_identity.api.model.DetailedUserDto
import org.thechance.service_identity.api.model.UserDto
import org.thechance.service_identity.data.collection.UserCollection
import org.thechance.service_identity.data.collection.UserDetailsCollection

data class User(
    val id: String? = null,
    val fullName: String?,
    val username: String?,
    val password: String?,
    val isDeleted: Boolean = false,
    val email: String? = null,
    val walletId: String? = null,
    val addresses: List<String> = emptyList(),
    val permissions: List<String> = emptyList()
) {
    fun toUserDto(): UserDto {
        return UserDto(
            id = id,
            fullName = fullName,
            username = username,
            password = password,
        )
    }

    fun toDetailedUserDto(): DetailedUserDto {
        return DetailedUserDto(
            id = id,
            fullName = fullName,
            username = username,
            password = password,
            email = email,
            walletId = walletId,
            addresses = addresses,
            permissions = permissions
        )
    }

    fun toUserCollection(): UserCollection {
        return UserCollection(
            fullName = this.fullName,
            username = this.username,
            password = this.password,
            isDeleted = this.isDeleted,
        )
    }

    fun toDetailsCollection(userId: String): UserDetailsCollection {
        return UserDetailsCollection(
            userId = ObjectId(userId),
            email = email,
            walletId = walletId,
            addresses = addresses.map { ObjectId(it) },
            permissions = permissions.map { ObjectId(it) }
        )
    }
}
