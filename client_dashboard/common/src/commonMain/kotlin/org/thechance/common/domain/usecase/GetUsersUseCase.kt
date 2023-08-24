package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.User
import org.thechance.common.domain.getway.IRemoteGateway

interface IGetUsersUseCase {
    operator fun invoke(page:Int,numberOfUsers:Int): DataWrapper<User>
}

class GetUsersUseCase(private val remoteGateway: IRemoteGateway) : IGetUsersUseCase {
    override fun invoke(page:Int,numberOfUsers: Int): DataWrapper<User> {
        return remoteGateway.getUsers(page,numberOfUsers)
    }
}