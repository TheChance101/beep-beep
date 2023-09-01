package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.NewTaxiInfo
import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.getway.IRemoteGateway

interface IDeleteTaxiUseCase {

    suspend fun deleteTaxi(taxiId: String): Boolean

}

class DeleteTaxiUseCase(private val remoteGateway: IRemoteGateway) : IDeleteTaxiUseCase {

    override suspend fun deleteTaxi(taxiId: String): Boolean {
        return remoteGateway.deleteTaxi(taxiId)
    }

}