package domain.usecase

import domain.gateway.IFakeRemoteGateway
import domain.gateway.local.ILocalConfigurationGateway

interface ILoginUserUseCase {

    suspend fun loginUser(
        userName: String,
        password: String,
        isKeepMeLoggedInChecked: Boolean
    )

}

class LoginUserUseCase(
    private val remoteGateway: IFakeRemoteGateway,
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