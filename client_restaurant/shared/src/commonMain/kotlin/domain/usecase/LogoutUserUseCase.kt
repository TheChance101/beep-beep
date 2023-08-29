package domain.usecase

import domain.gateway.local.ILocalConfigurationGateway

interface ILogoutUserUseCase {
    suspend fun logoutUser()
}

class LogoutUserUseCase(private val localGateWay: ILocalConfigurationGateway) : ILogoutUserUseCase {
    override suspend fun logoutUser() {
        localGateWay.clearTokens()
    }

}