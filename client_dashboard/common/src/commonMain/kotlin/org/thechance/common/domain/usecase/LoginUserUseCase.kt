package org.thechance.common.domain.usecase


import org.thechance.common.domain.getway.ILocationGateway
import org.thechance.common.domain.getway.IUserLocalGateway
import org.thechance.common.domain.getway.IUsersGateway

interface ILoginUserUseCase {

    suspend fun loginUser(username: String, password: String)

}

class LoginUserUseCase(
    private val userLocalGateway: IUserLocalGateway,
    private val usersRemoteGateway: IUsersGateway,
    private val locationRemoteGateway: ILocationGateway
) : ILoginUserUseCase {

    override suspend fun loginUser(username: String, password: String) {
        val userTokens = usersRemoteGateway.loginUser(username, password)
        val countryCode = locationRemoteGateway.getCurrentLocation().countryCode
        userLocalGateway.createUserConfiguration()
        userLocalGateway.saveAccessToken(userTokens.first)
        userLocalGateway.saveRefreshToken(userTokens.second)
        userLocalGateway.saveUserName(username)
        userLocalGateway.saveCountryCode(countryCode)
    }

}