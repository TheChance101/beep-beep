package org.thechance.service_identity.entity

import org.thechance.service_identity.api.model.UserDto

data class User(
    val id: String,
    val fullName: String,
    val username: String,
    val isDeleted: Boolean
){
    fun toUserDto(): UserDto {
        return UserDto(
            id = id,
            fullName = fullName,
            username = username,
            isDeleted = isDeleted
        )
    }
}
