package org.thechance.service_identity.domain.usecases.address

import org.thechance.service_identity.domain.entity.Address

interface UpdateAddressUseCase {
    suspend operator fun invoke(id: String, address: Address): Boolean

}