package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.User
import org.thechance.common.domain.getway.IRemoteGateway

interface ISearchUsersUseCase {
    suspend operator fun invoke(query: String, page: Int, numberOfUsers: Int): DataWrapper<User>
}

class SearchUsersUseCase(
    private val remoteGateway: IRemoteGateway,
) : ISearchUsersUseCase {
    override suspend fun invoke(query: String, page: Int, numberOfUsers: Int): DataWrapper<User> {
        return remoteGateway.searchUsers(query, page, numberOfUsers)
    }
}