package org.thechance.common.domain.usecase

import org.thechance.common.data.remote.gateway.FakeRemoteGateway
import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.User

interface IGetUsersUseCase {
    suspend operator fun invoke(query: String, page: Int, numberOfUsers: Int): DataWrapper<User>
}

class GetUsersUseCase(
    private val fakeRemoteGateway: FakeRemoteGateway,
    private val searchUsers: ISearchUsersUseCase

) : IGetUsersUseCase {
    override suspend operator fun invoke(query: String, page: Int, numberOfUsers: Int): DataWrapper<User> {
        return if (query.isEmpty()) {
            fakeRemoteGateway.getUsers(page, numberOfUsers)
        } else {
            searchUsers(query, page, numberOfUsers)
        }
    }
}