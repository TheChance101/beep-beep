package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.User
import org.thechance.common.domain.getway.IRemoteGateway


interface IGetUsersUseCase {
    operator fun invoke(): List<User>
}

class GetUsersUseCase(private val remoteGateway: IRemoteGateway) : IGetUsersUseCase {
    override operator fun invoke(): List<User> {
        return remoteGateway.getUsers()
    }
}