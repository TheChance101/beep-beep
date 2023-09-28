package org.thechance.service_identity.data.collection.mappers

import org.thechance.service_identity.data.collection.UserCollection
import org.thechance.service_identity.domain.entity.User

fun User.toCollection(hash:String,salt:String)= UserCollection(
    username = username,
    fullName = fullName,
    phone = phone,
    email = email,
    country = country,

    hashedPassword = hash,
    salt = salt,

)