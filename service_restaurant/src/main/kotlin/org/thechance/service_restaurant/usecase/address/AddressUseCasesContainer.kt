package org.thechance.service_restaurant.usecase.address

import org.koin.core.annotation.Single

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