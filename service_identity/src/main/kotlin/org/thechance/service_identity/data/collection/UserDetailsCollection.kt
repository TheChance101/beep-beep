package org.thechance.service_identity.data.collection

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.thechance.service_identity.entity.UserDetails

data class UserDetailsCollection(
    @BsonId
    val id: ObjectId = ObjectId(),
    val userId: Id<UserCollection>,
    val password: String,
    val email: String,
    val wallet: WalletCollection,
    val addresses: List<AddressCollection>,
    val permissions: List<PermissionCollection>
) {
    fun toUserDetails(): UserDetails {
        return UserDetails(
            id = id.toHexString(),
            userId = userId.toString(),
            password = password,
            email = email,
            wallet = wallet.toWallet(),
            addresses = addresses.map { it.toAddress() },
            permissions = permissions.map { it.toPermission() }
        )
    }
}


