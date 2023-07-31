package org.thechance.service_identity.domain.usecase.address

import org.thechance.service_identity.domain.entity.Address

interface GetUserAddressesUseCase {
    suspend operator fun invoke(userId : String) : List<Address>
}