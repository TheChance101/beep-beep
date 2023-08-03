package org.thechance.service_restaurant.usecase.address

import org.thechance.service_restaurant.entity.Address

interface GetAddressesUseCase {
    suspend operator fun invoke(): List<Address>
}

interface GetAddressDetailsUseCase {
    suspend operator fun invoke(addressId: String): Address
}

interface CreateAddressUseCase {
    suspend operator fun invoke(address: Address): Boolean
}

interface UpdateAddressUseCase {
    suspend operator fun invoke(address: Address): Boolean
}

interface DeleteAddressUseCase {
    suspend operator fun invoke(addressId: String): Boolean
}

interface AddAddressToRestaurantUseCase {
    suspend operator fun invoke(restaurantId: String, addressesIds: List<String>): Boolean
}

interface GetAddressesInRestaurantUseCase {
    suspend operator fun invoke(restaurantId: String): List<Address>
}

interface DeleteAddressesInRestaurantUseCase {
    suspend operator fun invoke(restaurantId: String, addressesIds: List<String>): Boolean
}
