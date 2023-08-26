package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.getway.IRemoteGateway


interface IFindTaxiByUsernameUseCase {
    suspend fun findTaxiByUsername(username: String, page:Int, offset:Int): DataWrapper<Taxi>
}

class FindTaxiByUserNameUseCase(
    private val remoteGateway: IRemoteGateway,
) : IFindTaxiByUsernameUseCase {
    override suspend fun findTaxiByUsername(username: String, page:Int, offset:Int): DataWrapper<Taxi> {
        return if (username.isEmpty()) remoteGateway.getTaxis(page,offset)
        else  remoteGateway.findTaxiByUsername(username,page,offset)
    }

}