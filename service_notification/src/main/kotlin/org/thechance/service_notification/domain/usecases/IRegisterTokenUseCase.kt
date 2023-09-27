package org.thechance.service_notification.domain.usecases

import org.koin.core.annotation.Single
import org.thechance.service_notification.domain.gateway.IDatabaseGateway


interface IRegisterTokenUseCase {
    suspend  fun saveToken(userId: String, token: String): Boolean

    suspend fun getUserTokens(id: String): List<String>

    suspend fun getAllUsersTokens(ids: List<String>): List<String>
}

@Single
class RegisterTokenUseCase(
    private val databaseGateway: IDatabaseGateway
) : IRegisterTokenUseCase {

    override suspend fun saveToken(userId: String, token: String): Boolean {
        return databaseGateway.registerToken(userId, token)
    }

    override suspend fun getUserTokens(id: String): List<String> {
        return databaseGateway.getUserTokens(id)
    }

    override suspend fun getAllUsersTokens(ids: List<String>): List<String> {
        return databaseGateway.getUsersTokens(ids)
    }

}