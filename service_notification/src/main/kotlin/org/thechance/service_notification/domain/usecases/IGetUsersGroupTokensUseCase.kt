package org.thechance.service_notification.domain.usecases


interface IGetUsersGroupTokensUseCase {
    suspend fun getUsersGroupTokens(usersGroup: String): List<String>
}