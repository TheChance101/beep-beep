package org.thechance.common.domain.usecase

import org.thechance.common.data.remote.gateway.FakeRemoteGateway
import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.User

interface IGetUsersUseCase {
    suspend operator fun invoke(page:Int, numberOfUsers:Int): DataWrapper<User>
}

class GetUsersUseCase(private val fakeRemoteGateway: FakeRemoteGateway) : IGetUsersUseCase {
    override suspend operator fun invoke(page:Int,numberOfUsers: Int): DataWrapper<User> {
        return fakeRemoteGateway.getUsers(page,numberOfUsers)
    }
}