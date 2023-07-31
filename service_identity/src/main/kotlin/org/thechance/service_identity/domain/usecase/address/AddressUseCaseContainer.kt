package org.thechance.service_identity.domain.usecase.address

import org.koin.core.annotation.Single


@Single
data class AddressUseCaseContainer(
    val createAddressUseCase: CreateAddressUseCase,
    val getAddressUseCase: GetAddressUseCase,
    val getUserAddressesUseCase: GetUserAddressesUseCase,
    val deleteAddressUseCase: DeleteAddressUseCase,
    val updateAddressUseCase: UpdateAddressUseCase
)