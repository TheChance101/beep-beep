package org.thechance.service_notification.domain.usecases

import org.koin.core.annotation.Single
import org.thechance.service_notification.domain.gateway.IDatabaseGateway

@Single
class GetUsersGroupTokensUseCase(
    private val databaseGateway: IDatabaseGateway
) : IGetUsersGroupTokensUseCase {
    override suspend fun invoke(usersGroup: String): List<String> {
        val ids = databaseGateway.getUsersGroupIds(usersGroup)
        return databaseGateway.getUsersTokens(ids)
    }

}

interface IGetUsersGroupTokensUseCase {
    suspend operator fun invoke(usersGroup: String): List<String>
}