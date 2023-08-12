package org.thechance.service_identity.domain.usecases

import org.koin.core.annotation.Single
import org.thechance.service_identity.domain.entity.Address
import org.thechance.service_identity.domain.entity.CreateAddressRequest
import org.thechance.service_identity.domain.entity.UpdateAddressRequest
import org.thechance.service_identity.domain.gateway.IDataBaseGateway
import org.thechance.service_identity.domain.usecases.validation.IAddressValidationUseCase

interface IUserAddressManagementUseCase {

    suspend fun addAddress(userId: String, address: CreateAddressRequest): Boolean

    suspend fun getAddress(id: String): Address

    suspend fun deleteAddress(id: String): Boolean

    suspend fun updateAddress(id: String, address: UpdateAddressRequest): Boolean

    suspend fun getUserAddresses(userId: String): List<Address>

}

@Single
class UserAddressManagementUseCase(
    private val dataBaseGateway: IDataBaseGateway,
    private val validateAddress: IAddressValidationUseCase
) : IUserAddressManagementUseCase {

    override suspend fun addAddress(userId: String, address: CreateAddressRequest): Boolean {
        validateAddress.validateCreateAddressRequest(address)
        return dataBaseGateway.addAddress(userId, address)
    }

    override suspend fun deleteAddress(id: String): Boolean {
        return dataBaseGateway.deleteAddress(id)
    }

    override suspend fun updateAddress(id: String, address: UpdateAddressRequest): Boolean {
        validateAddress.validateUpdateAddressRequest(address)
        return dataBaseGateway.updateAddress(id, address)
    }

    override suspend fun getAddress(id: String): Address {
        return dataBaseGateway.getAddress(id)
    }

    override suspend fun getUserAddresses(userId: String): List<Address> {
        return dataBaseGateway.getUserAddresses(userId)
    }

}