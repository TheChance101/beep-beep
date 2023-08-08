package org.thechance.service_notification.data.mappers

import org.bson.types.ObjectId
import org.thechance.service_notification.data.collection.UserCollection
import org.thechance.service_notification.domain.model.User
import org.thechance.service_notification.endpoints.model.UserDto

fun User.toCollection(): UserCollection {
    return UserCollection(
        id = ObjectId(id),
        deviceTokens = deviceTokens,
        topics = topics,
        notifications = notifications.map { it.toCollection() }
    )
}
fun User.toDto(): UserDto {
    return UserDto(
        id = id,
        deviceTokens = deviceTokens,
        topics = topics,
        notifications = notifications.toDto()
    )
}
fun UserDto.toEntity(): User {
    return User(
        id = id,
        deviceTokens = deviceTokens,
        topics = topics,
        notifications = notifications.toEntity()
    )
}
fun UserCollection.toEntity(): User {
    return User(
        id = id.toHexString(),
        deviceTokens = deviceTokens,
        topics = topics,
        notifications = notifications.toNotificationEntity()
    )
}

fun List<User>.toDto(): List<UserDto> {
    return this.map { it.toDto() }
}
fun List<UserCollection>.toEntity(): List<User> {
    return this.map { it.toEntity() }
}



