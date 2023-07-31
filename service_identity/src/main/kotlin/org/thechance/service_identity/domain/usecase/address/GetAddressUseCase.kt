package org.thechance.service_identity.domain.usecase.address

import org.thechance.service_identity.domain.entity.Address

interface GetAddressUseCase {
    suspend operator fun invoke(id : String) : Address
}