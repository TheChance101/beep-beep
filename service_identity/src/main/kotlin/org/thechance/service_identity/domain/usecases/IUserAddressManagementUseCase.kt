package org.thechance.service_identity.domain.usecases

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.Address
import org.thechance.service_identity.domain.entity.Location
import org.thechance.service_identity.domain.gateway.IDataBaseGateway

interface IUserAddressManagementUseCase {

    suspend fun addAddress(userId: String, location: Location): Boolean

    suspend fun getAddress(id: String): Address

    suspend fun deleteAddress(id: String): Boolean

    suspend fun updateAddress(id: String, location: Location): Address

    suspend fun getUserAddresses(userId: String): List<Address>

}

@Single
class UserAddressManagementUseCase(
    private val dataBaseGateway: IDataBaseGateway,
) : IUserAddressManagementUseCase {

    override suspend fun addAddress(userId: String, location: Location): Boolean {
        return dataBaseGateway.addAddress(userId, location)
    }

    override suspend fun deleteAddress(id: String): Boolean {
        return dataBaseGateway.deleteAddress(id)
    }

    override suspend fun updateAddress(id: String, location: Location): Address {
        return dataBaseGateway.updateAddress(id, location)
    }

    override suspend fun getAddress(id: String): Address {
        return dataBaseGateway.getAddress(id)
    }

    override suspend fun getUserAddresses(userId: String): List<Address> {
        return dataBaseGateway.getUserAddresses(userId)
    }

}