package org.thechance.service_identity.data.geteway

import com.mongodb.client.model.Filters
import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.*
import org.thechance.service_identity.data.DataBaseContainer
import org.thechance.service_identity.data.collection.AddressCollection
import org.thechance.service_identity.data.collection.UserDetailsCollection
import org.thechance.service_identity.data.mappers.toCollection
import org.thechance.service_identity.data.mappers.toEntity
import org.thechance.service_identity.data.util.USER_DETAILS_COLLECTION
import org.thechance.service_identity.data.util.isUpdatedSuccessfully
import org.thechance.service_identity.domain.entity.Address
import org.thechance.service_identity.domain.gateway.AddressGateway

private const val ADDRESS_COLLECTION_NAME = "address"

@Single
class AddressGatewayImp(dataBaseContainer: DataBaseContainer) : AddressGateway {

    private val addressCollection by lazy {
        dataBaseContainer.database.getCollection<AddressCollection>(ADDRESS_COLLECTION_NAME)
    }

    private val userDetailsCollection by lazy {
        dataBaseContainer.database.getCollection<UserDetailsCollection>(USER_DETAILS_COLLECTION)
    }

    override suspend fun addAddress(address: Address): Boolean {
        val newAddressCollection = address.toCollection()
        userDetailsCollection.updateOne(
            filter = UserDetailsCollection::userId eq newAddressCollection.userId,
            update = push(UserDetailsCollection::addresses, newAddressCollection.id)
        )
        return addressCollection.insertOne(newAddressCollection).wasAcknowledged()
    }

    override suspend fun deleteAddress(id: String): Boolean {
        userDetailsCollection.updateOne(
            filter = UserDetailsCollection::addresses contains ObjectId(id),
            update = pull(UserDetailsCollection::addresses, ObjectId(id))
        )
        return addressCollection.updateOne(
            filter = Filters.and(AddressCollection::id eq ObjectId(id), AddressCollection::isDeleted eq false),
            update = setValue(AddressCollection::isDeleted, true)
        ).isUpdatedSuccessfully()
    }

    override suspend fun updateAddress(id: String, address: Address): Boolean {
        return addressCollection.updateOneById(ObjectId(id), address.toCollection()).isUpdatedSuccessfully()
    }

    override suspend fun getAddress(id: String): Address? {
        return addressCollection.findOne(
            AddressCollection::id eq ObjectId(id),
            AddressCollection::isDeleted eq false
        )?.toEntity()
    }

    override suspend fun getUserAddresses(userId: String): List<Address> {
        return addressCollection.find(
            AddressCollection::userId eq ObjectId(userId),
            AddressCollection::isDeleted eq false
        ).toList().toAddress()
    }
}

private fun List<AddressCollection>.toAddress(): List<Address> {
    return this.map { it.toEntity() }
}

