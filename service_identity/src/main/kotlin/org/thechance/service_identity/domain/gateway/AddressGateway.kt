package org.thechance.service_identity.domain.gateway

import org.thechance.service_identity.domain.entity.Address

interface AddressGateway {
    suspend fun addAddress(address: Address): Boolean

    suspend fun deleteAddress(id: String): Boolean

    suspend fun updateAddress(id: String, address: Address): Boolean

    suspend fun getAddress(id: String): Address?

    suspend fun getUserAddresses(userId: String): List<Address>

}