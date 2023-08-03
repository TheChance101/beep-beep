package org.thechance.service_restaurant.api.usecases

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.entity.Address


@Single
data class AddressUseCasesContainer(
    val getAddresses: GetAddressesUseCase,
    val getAddressDetails: GetAddressDetailsUseCase,
    val createAddress: CreateAddressUseCase,
    val updateAddress: UpdateAddressUseCase,
    val deleteAddress: DeleteAddressUseCase,
    val addAddressToRestaurant: AddAddressToRestaurantUseCase,
    val getAddressesInRestaurant: GetAddressesInRestaurantUseCase,
    val deleteAddressesInRestaurant: DeleteAddressesInRestaurantUseCase,
)

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
