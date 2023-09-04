package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.Permission
import org.thechance.common.domain.entity.User
import org.thechance.common.domain.getway.IRemoteGateway

interface IGetUsersUseCase {

    suspend operator fun invoke(
        query: String? = null,
        byPermissions: List<Permission>,
        byCountries: List<String>,
        page: Int,
        numberOfUsers: Int
    ): DataWrapper<User>

}

class GetUsersUseCase(
    private val remoteGateway: IRemoteGateway,
) : IGetUsersUseCase {

    override suspend fun invoke(
        query: String?,
        byPermissions: List<Permission>,
        byCountries: List<String>,
        page: Int,
        numberOfUsers: Int,
    ): DataWrapper<User> {
        return remoteGateway.getUsers(query, byPermissions, byCountries, page, numberOfUsers)
    }

}