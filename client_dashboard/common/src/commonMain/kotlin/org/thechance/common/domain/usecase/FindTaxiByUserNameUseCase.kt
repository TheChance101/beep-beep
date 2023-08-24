package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.getway.IRemoteGateway


interface IFindTaxiByUsernameUseCase {
    suspend fun findTaxiByUsername(username:String): List<Taxi>
}

class FindTaxiByUserNameUseCase(
    private val remoteGateway: IRemoteGateway,
) : IFindTaxiByUsernameUseCase {
    override suspend fun findTaxiByUsername(username: String): List<Taxi> {
        return if (username.isEmpty()) remoteGateway.getTaxis()
        else  remoteGateway.findTaxiByUsername(username)
    }

}