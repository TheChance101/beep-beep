package org.thechance.service_identity.domain.usecase.address

import org.thechance.service_identity.domain.entity.Address

interface CreateAddressUseCase {
    suspend operator fun invoke(address: Address) : Boolean
}