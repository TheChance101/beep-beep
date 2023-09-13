package domain.usecase

import domain.gateway.IIdentityRemoteGateway

interface ILoginUserUseCase {


}

class LoginUserUseCase(
    private val remoteGateway: IIdentityRemoteGateway,
) : ILoginUserUseCase {




}