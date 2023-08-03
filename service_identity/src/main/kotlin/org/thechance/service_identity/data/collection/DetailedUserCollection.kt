package org.thechance.service_identity.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.thechance.service_identity.domain.entity.User

@Serializable
data class DetailedUserCollection(
    @SerialName("_id")
    @BsonId
    @Contextual
    val id: ObjectId,
    @SerialName("full_name")
    val fullName: String?,
    @SerialName("user_name")
    val username: String?,
    @SerialName("password")
    val password: String?,
    @SerialName("isDeleted")
    val isDeleted: Boolean = false,
    @SerialName("details")
    val details: List<UserDetailsCollection>
)

fun DetailedUserCollection.toEntity(): User {
    return User(
        id = id.toHexString(),
        fullName = fullName,
        username = username,
        password = password,
        isDeleted = isDeleted,
        email = details.first().email,
        walletId = details.first().walletId,
        addresses = details.first().addresses.map { it.toHexString() },
        permissions = details.first().permissions.map { it.toHexString() }
    )
}

fun List<DetailedUserCollection>.toEntity(): List<User> {
    return map { it.toEntity() }
}