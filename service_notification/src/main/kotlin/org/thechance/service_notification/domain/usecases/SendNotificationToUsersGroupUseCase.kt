package org.thechance.service_notification.domain.usecases

import org.koin.core.annotation.Single

@Single
class SendNotificationToUsersGroupUseCase(
    private val getUsersGroupTokensUseCase: IGetUsersGroupTokensUseCase,
    private val sendNotificationUseCase: ISendNotificationUseCase
) : ISendNotificationToUsersGroupUseCase {
    override suspend fun invoke(usersGroup: String, title: String, body: String): Boolean {
        val tokens = getUsersGroupTokensUseCase(usersGroup)
        println("********* tokens: $tokens")
        return sendNotificationUseCase(tokens, title, body)
    }

}

interface ISendNotificationToUsersGroupUseCase {
    suspend operator fun invoke(usersGroup: String, title: String, body: String): Boolean
}