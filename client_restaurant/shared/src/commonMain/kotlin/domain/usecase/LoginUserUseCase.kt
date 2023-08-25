package domain.usecase

import domain.entity.UserTokens
import domain.gateway.ILocalGateWay
import domain.gateway.IRemoteGateWay

interface ILoginUserUseCase {

    suspend fun loginUser(
        userName: String,
        password: String,
        isKeepMeLoggedInChecked: Boolean
    ): UserTokens

    suspend fun saveAccessToken(token: String)
    suspend fun getAccessToken(): String
    suspend fun saveRefreshToken(token: String)
    suspend fun getRefreshToken(): String
    suspend fun saveLoggedInFlag(isChecked: Boolean)
    suspend fun getLoggedInFlag(): Boolean
}

class LoginUserUseCase(
    private val remoteGateWay: IRemoteGateWay,
    private val localGateWay: ILocalGateWay
) : ILoginUserUseCase {

    override suspend fun loginUser(
        userName: String,
        password: String,
        isKeepMeLoggedInChecked: Boolean
    ): UserTokens {
        localGateWay.saveKeepMeLoggedInFlag(isKeepMeLoggedInChecked)
        return remoteGateWay.loginUser(userName, password)
    }


    override suspend fun saveAccessToken(token: String) = localGateWay.saveAccessToken(token)


    override suspend fun getAccessToken(): String = localGateWay.getAccessToken()


    override suspend fun saveRefreshToken(token: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getRefreshToken(): String = localGateWay.getRefreshToken()

    override suspend fun saveLoggedInFlag(isChecked: Boolean) {
        localGateWay.saveKeepMeLoggedInFlag(isChecked)
    }

    override suspend fun getLoggedInFlag(): Boolean = localGateWay.getKeepMeLoggedInFlag()

}