package org.thechance.common.data.remote.model

import org.thechance.common.domain.entity.User


data class UserDto(
    val id: Int,
    val name: String,
    val role: String,
) {
    fun toEntity() = User(
        id = id,
        name = name,
        role = role,
    )
}
