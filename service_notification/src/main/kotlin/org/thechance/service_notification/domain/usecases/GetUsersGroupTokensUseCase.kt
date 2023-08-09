package org.thechance.service_notification.domain.usecases

import org.koin.core.annotation.Single
import org.thechance.service_notification.domain.gateway.IDatabaseGateway

@Single
class GetUsersGroupTokensUseCase(
    private val databaseGateway: IDatabaseGateway
) : IGetUsersGroupTokensUseCase {
    override suspend fun getUsersGroupTokens(usersGroup: String): List<String> {
        return databaseGateway.getUsersGroupTokens(usersGroup)
    }

}