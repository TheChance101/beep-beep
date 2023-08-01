package org.thechance.service_identity.domain.usecases.address

interface DeleteAddressUseCase {
    suspend operator fun invoke(id : String) : Boolean
}