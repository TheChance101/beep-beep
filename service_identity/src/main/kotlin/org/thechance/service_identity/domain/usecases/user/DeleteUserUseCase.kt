package org.thechance.service_identity.domain.usecases.user

interface DeleteUserUseCase {
    suspend operator fun invoke(id: String): Boolean
}