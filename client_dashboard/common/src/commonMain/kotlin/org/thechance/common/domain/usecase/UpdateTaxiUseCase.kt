package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.NewTaxiInfo
import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.getway.IRemoteGateway

interface IUpdateTaxiUseCase {

    suspend fun updateTaxi(addTaxi: NewTaxiInfo): Boolean

}

class UpdateTaxiUseCase(private val remoteGateway: IRemoteGateway) : IUpdateTaxiUseCase {

    override suspend fun updateTaxi(addTaxi: NewTaxiInfo): Boolean {
        return remoteGateway.updateTaxi(addTaxi)
    }

}