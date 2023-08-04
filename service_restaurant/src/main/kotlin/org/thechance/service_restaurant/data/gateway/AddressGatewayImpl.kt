package org.thechance.service_restaurant.data.gateway

import com.mongodb.client.model.Updates
import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.ne
import org.thechance.service_restaurant.data.DataBaseContainer
import org.thechance.service_restaurant.data.collection.AddressCollection
import org.thechance.service_restaurant.data.collection.mapper.toCollection
import org.thechance.service_restaurant.data.collection.mapper.toEntity
import org.thechance.service_restaurant.data.utils.isSuccessfullyUpdated
import org.thechance.service_restaurant.domain.entity.Address
import org.thechance.service_restaurant.domain.gateway.AddressGateway

@Single
class AddressGatewayImpl(private val container: DataBaseContainer) : AddressGateway {

    override suspend fun getAddresses(): List<Address> {
        return container.addressCollection.find(AddressCollection::isDeleted ne true).toList().toEntity()
    }

    override suspend fun getAddressDetails(id: String): Address? {
        return container.addressCollection.findOneById(ObjectId(id))?.takeIf { !it.isDeleted }?.toEntity()
    }

    override suspend fun createAddress(address: Address): Boolean {
        return container.addressCollection.insertOne(address.toCollection()).wasAcknowledged()
    }

    override suspend fun updateAddress(address: Address): Boolean {
        return container.addressCollection.updateOneById(
            id = ObjectId(address.id),
            update = address.toCollection(),
            updateOnlyNotNullProperties = true
        ).isSuccessfullyUpdated()
    }

    override suspend fun deleteAddress(id: String): Boolean {
        return container.addressCollection.updateOneById(
            id = ObjectId(id),
            update = Updates.set(AddressCollection::isDeleted.name, true),
        ).isSuccessfullyUpdated()
    }
}
