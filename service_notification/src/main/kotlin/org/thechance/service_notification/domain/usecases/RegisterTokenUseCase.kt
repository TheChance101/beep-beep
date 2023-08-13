package org.thechance.service_notification.domain.usecases

import org.koin.core.annotation.Single
import org.thechance.service_notification.domain.gateway.IDatabaseGateway

@Single
class RegisterTokenUseCase(
    private val databaseGateway: IDatabaseGateway
) : IRegisterTokenUseCase {

    override suspend fun invoke(userId: String, token: String): Boolean {
        return databaseGateway.registerToken(userId, token)
    }

}

interface IRegisterTokenUseCase {

    suspend operator fun invoke(userId: String, token: String): Boolean

}