package data.remote.model

import domain.entity.User

class UserDto{
    var username: String = ""
    var password: String = ""
}
fun UserDto.toEntity(): User = User(
    username = username,
    password = password
)