package org.thechance.service_identity.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.thechance.service_identity.domain.entity.Address
import org.thechance.service_identity.domain.util.Role

@Serializable
data class DetailedUser(
    @SerialName("_id")
    @Contextual
    val id: ObjectId,
    val fullName: String,
    val username: String,
    val email: String,
    val phone: String = "",
    val country: String = "",
    val permission: Int = Role.END_USER,
    val wallet: WalletCollection,
    val addresses: List<AddressCollection> = emptyList()
)