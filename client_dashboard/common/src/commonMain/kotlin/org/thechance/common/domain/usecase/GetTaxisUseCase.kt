package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.entity.User
import org.thechance.common.domain.getway.IRemoteGateway

interface IGetTaxisUseCase {
    suspend fun getTaxis(page:Int,numberOfUsers: Int): DataWrapper<Taxi>
}

class GetTaxisUseCase(
    private val remoteGateway: IRemoteGateway,
) : IGetTaxisUseCase {
    override suspend fun getTaxis(page:Int,numberOfUsers: Int): DataWrapper<Taxi> {
        return remoteGateway.getTaxis(page,numberOfUsers)
    }
}