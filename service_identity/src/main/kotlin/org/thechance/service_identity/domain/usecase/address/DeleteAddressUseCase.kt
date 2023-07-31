package org.thechance.service_identity.domain.usecase.address

interface DeleteAddressUseCase {
    suspend operator fun invoke(id : String) : Boolean
}