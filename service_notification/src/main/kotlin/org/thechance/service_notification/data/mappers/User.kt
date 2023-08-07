package org.thechance.service_notification.data.mappers

import org.bson.types.ObjectId
import org.thechance.service_notification.data.collection.UserCollection
import org.thechance.service_notification.domain.entity.User
import org.thechance.service_notification.endpoints.model.UserDto

fun User.toCollection(): UserCollection {
    return UserCollection(
        id = ObjectId(id),
        deviceTokens = deviceTokens,
        topics = topics,
        notifications = notifications
    )
}
fun User.toDto(): UserDto {
    return UserDto(
        id = id,
        deviceTokens = deviceTokens,
        topics = topics,
        notifications = notifications
    )
}
fun UserDto.toEntity(): User {
    return User(
        id = id,
        deviceTokens = deviceTokens,
        topics = topics,
        notifications = notifications
    )
}
fun UserCollection.toEntity(): User {
    return User(
        id = id.toHexString(),
        deviceTokens = deviceTokens,
        topics = topics,
        notifications = notifications
    )
}

fun List<User>.toDto(): List<UserDto> {
    return this.map { it.toDto() }
}
fun List<UserCollection>.toEntity(): List<User> {
    return this.map { it.toEntity() }
}



