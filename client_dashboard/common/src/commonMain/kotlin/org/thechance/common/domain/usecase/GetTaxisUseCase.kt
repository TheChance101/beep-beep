package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.getway.IRemoteGateway


interface IGetTaxisUseCase {
    suspend fun getTaxis(): List<Taxi>
}

class GetTaxisUseCase(
    private val remoteGateway: IRemoteGateway,
) : IGetTaxisUseCase {
    override suspend fun getTaxis(): List<Taxi> {
        return remoteGateway.getTaxis()
    }
}