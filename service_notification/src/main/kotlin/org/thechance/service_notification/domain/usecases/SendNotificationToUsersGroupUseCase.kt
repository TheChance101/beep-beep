package org.thechance.service_notification.domain.usecases

import org.koin.core.annotation.Single

@Single
class SendNotificationToUsersGroupUseCase(
    private val getUsersGroupTokensUseCase: IGetUsersGroupTokensUseCase,
    private val sendNotificationUseCase: ISendNotificationUseCase
) : ISendNotificationToUsersGroupUseCase {
    override suspend fun sendNotificationToUsersGroup(usersGroup: String, title: String, body: String) {
        val tokens = getUsersGroupTokensUseCase.getUsersGroupTokens(usersGroup)
        sendNotificationUseCase.sendNotification(tokens, title, body)
    }

}