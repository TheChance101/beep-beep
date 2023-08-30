package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.User
import org.thechance.common.domain.getway.IRemoteGateway

interface ISearchUsersUseCase {
    suspend operator fun invoke(query: String, page: Int, numberOfUsers: Int): DataWrapper<User>
}

class SearchUsersUseCase(
    private val remoteGateway: IRemoteGateway,
    private val getAllUsers: IGetUsersUseCase
) : ISearchUsersUseCase {
    override suspend fun invoke(query: String, page: Int, numberOfUsers: Int): DataWrapper<User> {
        return if (query.isEmpty()) getAllUsers(page, numberOfUsers)
        else remoteGateway.searchUsers(query, page, numberOfUsers)
    }
}