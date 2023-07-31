package org.thechance.service_identity.data.collection

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.thechance.service_identity.entity.User

data class UserCollection(
    @BsonId
    val id: ObjectId = ObjectId(),
    val fullName: String,
    val username: String,
    val isDeleted: Boolean = false
){
    fun toUser(): User {
        return User(
            id = id.toHexString(),
            fullName = fullName,
            username = username,
            isDeleted = isDeleted
        )
    }
}

