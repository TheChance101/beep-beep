package org.thechance.service_restaurant.data.gateway

import org.thechance.service_restaurant.entity.Address

interface AddressGateway {
    suspend fun getAddresses(): List<Address>
    suspend fun getAddressDetails(id: String): Address?
    suspend fun createAddress(address: Address): Boolean
    suspend fun updateAddress(address: Address): Boolean
    suspend fun deleteAddress(id: String): Boolean
}
