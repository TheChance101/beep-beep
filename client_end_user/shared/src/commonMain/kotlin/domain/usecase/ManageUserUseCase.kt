package domain.usecase

import data.gateway.remote.FakeRemoteGateway
import domain.entity.User
import domain.gateway.local.ILocalConfigurationGateway
import domain.utils.AuthorizationException

interface IManageUserUseCase {

    suspend fun getUserWallet(): User

}

class ManageUserUseCase(
    private val localGateway: ILocalConfigurationGateway,
    private val remoteGateway: FakeRemoteGateway
) : IManageUserUseCase {

    override suspend fun getUserWallet(): User {
       return if (localGateway.getAccessToken().isNotEmpty()) {
            remoteGateway.getUsrWallet()
        } else {
            throw AuthorizationException()
        }
    }


}
