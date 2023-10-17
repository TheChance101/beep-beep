package domain.usecase

import data.remote.fakegateway.IdentityFakeGateway


interface IIdentityUseCase {
    suspend fun getTaxiDriverName(): String
}

class IdentityUseCase(
    private val identityGateway: IdentityFakeGateway,
) : IIdentityUseCase {
    override suspend fun getTaxiDriverName() = identityGateway.getTaxiDriverName()
}