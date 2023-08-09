package org.thechance.service_identity.domain.usecases.user_address

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.Address
import org.thechance.service_identity.domain.gateway.DataBaseGateway

interface IManageUserAddressUseCase {

    suspend fun addAddress(userId: String, address: Address): Boolean

    suspend fun getAddress(id: String): Address

    suspend fun deleteAddress(id: String): Boolean

    suspend fun updateAddress(id: String, address: Address): Boolean

    suspend fun getUserAddresses(userId: String): List<Address>

}

@Single
class ManageUserAddressUseCase(private val dataBaseGateway: DataBaseGateway) : IManageUserAddressUseCase {

    override suspend fun addAddress(userId: String, address: Address): Boolean {
        return dataBaseGateway.addAddress(userId, address)
    }

    override suspend fun deleteAddress(id: String): Boolean {
        return dataBaseGateway.deleteAddress(id)
    }

    override suspend fun updateAddress(id: String, address: Address): Boolean {
        return dataBaseGateway.updateAddress(id, address)
    }

    override suspend fun getAddress(id: String): Address {
        return dataBaseGateway.getAddress(id)
    }

    override suspend fun getUserAddresses(userId: String): List<Address> {
        return dataBaseGateway.getUserAddresses(userId)
    }

}