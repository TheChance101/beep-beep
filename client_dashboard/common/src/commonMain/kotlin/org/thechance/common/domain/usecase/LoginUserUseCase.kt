package org.thechance.common.domain.usecase


import org.thechance.common.domain.getway.IUserLocalGateway
import org.thechance.common.domain.getway.IUsersGateway

interface ILoginUserUseCase {

    suspend fun loginUser(username: String, password: String)

}

class LoginUserUseCase(
    private val userLocalGateway: IUserLocalGateway,
    private val usersRemoteGateway: IUsersGateway
) : ILoginUserUseCase {

    override suspend fun loginUser(username: String, password: String) {
        val userTokens = usersRemoteGateway.loginUser(username, password)
        userLocalGateway.createUserConfiguration()
        userLocalGateway.saveAccessToken(userTokens.first)
        userLocalGateway.saveRefreshToken(userTokens.second)
        userLocalGateway.saveUserName(username)
    }

}