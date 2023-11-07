package org.thechance.service_identity.data.collection.mappers

import org.thechance.service_identity.data.collection.UserCollection
import org.thechance.service_identity.domain.entity.UserManagement

fun UserCollection.toManagedEntity() = UserManagement(
    id = id.toString(),
    fullName = fullName,
    username = username,
    email = email,
    country = country,
    phone = phone,
    permission = permission
)

fun List<UserCollection>.toManagedEntity() = map { it.toManagedEntity() }

