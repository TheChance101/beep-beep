package org.thechance.common.domain.usecase

import org.thechance.common.domain.getway.ILocalGateway

interface IGetUserTokensUseCase {

    suspend fun getAccessToken(): String

    suspend fun getRefreshToken(): String

}

class GetUserTokensUseCase(
    private val localGateway: ILocalGateway
) : IGetUserTokensUseCase {

    override suspend fun getAccessToken(): String {
        return localGateway.getAccessToken()
    }

    override suspend fun getRefreshToken(): String {
        return localGateway.getRefreshToken()
    }

}