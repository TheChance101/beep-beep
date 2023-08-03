package org.thechance.service_identity.data.mappers

import org.thechance.service_identity.data.collection.UserCollection
import org.thechance.service_identity.domain.entity.User

fun UserCollection.toUser(): User {
    return User(
        id = id.toHexString(),
        fullName = fullName,
        username = username,
        password = password,
        isDeleted = isDeleted
    )
}