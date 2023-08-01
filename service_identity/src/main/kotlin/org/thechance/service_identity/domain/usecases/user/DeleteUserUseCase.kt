package org.thechance.service_identity.domain.usecases.user

interface DeleteUserUseCase {
    suspend fun invoke(id: String): Boolean
}