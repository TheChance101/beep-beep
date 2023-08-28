package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.getway.IRemoteGateway


interface IFindTaxisByUsernameUseCase {
    suspend fun findTaxisByUsername(username: String, page:Int, numberOfTaxis:Int): DataWrapper<Taxi>
}

class FindTaxisByUserNameUseCase(
    private val remoteGateway: IRemoteGateway,
) : IFindTaxisByUsernameUseCase {
    override suspend fun findTaxisByUsername(username: String, page:Int, numberOfTaxis:Int): DataWrapper<Taxi> {
        return if (username.isEmpty()) remoteGateway.getTaxis(page,numberOfTaxis)
        else  remoteGateway.findTaxisByUsername(username,page,numberOfTaxis)
    }

}