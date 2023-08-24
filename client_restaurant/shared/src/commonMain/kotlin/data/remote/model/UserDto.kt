package data.remote.model

import domain.entity.User

/**
 * Created by Aziza Helmy on 8/24/2023.
 */

class UserDto{
    var username: String = ""
    var password: String = ""
}

fun UserDto.toEntity(): User = User(
    username = username,
    password = password
)