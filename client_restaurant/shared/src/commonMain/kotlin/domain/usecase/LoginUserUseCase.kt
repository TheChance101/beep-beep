package domain.usecase

import domain.gateway.ILocalConfigurationGateway
import domain.gateway.IRemoteIdentityGateway

interface ILoginUserUseCase {

    suspend fun loginUser(
        userName: String,
        password: String,
        isKeepMeLoggedInChecked: Boolean
    )

}

class LoginUserUseCase(
    private val remoteGateway: IRemoteIdentityGateway,
    private val localGateWay: ILocalConfigurationGateway
) : ILoginUserUseCase {

    override suspend fun loginUser(
        userName: String,
        password: String,
        isKeepMeLoggedInChecked: Boolean
    ) {
        val userTokens = remoteGateway.loginUser(userName, password)
        localGateWay.saveAccessToken(userTokens.first)
        localGateWay.saveRefreshToken(userTokens.second)
        localGateWay.saveKeepMeLoggedInFlag(isKeepMeLoggedInChecked)
    }

}