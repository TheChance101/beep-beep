package org.thechance.service_identity.data.geteway

import com.mongodb.client.model.Filters
import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.*
import org.litote.kmongo.id.toId
import org.thechance.service_identity.data.DataBaseContainer
import org.thechance.service_identity.data.collection.AddressCollection
import org.thechance.service_identity.data.collection.UserDetailsCollection
import org.thechance.service_identity.domain.entity.Address
import org.thechance.service_identity.domain.gateway.AddressGateway
import org.thechance.service_identity.utils.Constants.ADDRESS_COLLECTION_NAME
import org.thechance.service_identity.utils.isDocumentModified

@Single
class AddressGatewayImp(dataBaseContainer: DataBaseContainer) : AddressGateway {

    private val addressCollection by lazy {
        dataBaseContainer.database.getCollection<AddressCollection>(ADDRESS_COLLECTION_NAME)
    }

    private val userDetailsCollection by lazy {
        dataBaseContainer.database.getCollection<UserDetailsCollection>("user_details")
    }

    override suspend fun addAddress(address: Address): Boolean {
        userDetailsCollection.updateOne(
            filter = UserDetailsCollection::userId eq ObjectId(address.userId),
            update = push(UserDetailsCollection::addresses, ObjectId(address.id))
        )
        return addressCollection.insertOne(address.toAddressCollection()).wasAcknowledged()
    }

    override suspend fun deleteAddress(id: String): Boolean {
        userDetailsCollection.updateOne(
            filter = UserDetailsCollection::addresses contains ObjectId(id),
            update = pull(UserDetailsCollection::addresses, ObjectId(id))
        )
        return addressCollection.updateOne(
            filter = Filters.and(AddressCollection::id eq ObjectId(id), AddressCollection::isDeleted eq false),
            update = setValue(AddressCollection::isDeleted, true)
        ).isDocumentModified()
    }

    override suspend fun updateAddress(id: String, address: Address): Boolean {
        return addressCollection.updateOneById(ObjectId(id), address.toAddressCollection()).isDocumentModified()
    }

    override suspend fun getAddress(id: String): Address? {
        return addressCollection.findOne(
            AddressCollection::id eq ObjectId(id),
            AddressCollection::isDeleted eq false
        )?.toAddress()
    }

    override suspend fun getUserAddresses(userId: String): List<Address> {
        return addressCollection.find(
            AddressCollection::userId eq ObjectId(userId).toId(),
            AddressCollection::isDeleted eq false
        ).toList().toAddress()
    }
}

private fun List<AddressCollection>.toAddress(): List<Address> {
    return this.map { it.toAddress() }
}