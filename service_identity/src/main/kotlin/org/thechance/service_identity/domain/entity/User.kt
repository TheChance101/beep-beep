package org.thechance.service_identity.domain.entity

import org.thechance.service_identity.api.model.UserDto
import org.thechance.service_identity.data.collection.UserCollection

data class User(
    val id: String? = null,
    val fullName: String,
    val username: String,
    val isDeleted: Boolean? = null
){
    fun toUserDto(): UserDto {
        return UserDto(
            id = id,
            fullName = fullName,
            username = username,
        )
    }

    fun toUserCollection(): UserCollection {
        return UserCollection(
            fullName = this.fullName,
            username = this.username,
        )
    }
}
