package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.NewTaxiInfo
import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.getway.IRemoteGateway

interface ICreateNewTaxiUseCase {

    suspend fun createTaxi(addTaxi: NewTaxiInfo): Taxi

}

class CreateNewTaxiUseCase(private val fakeRemoteGateway: IRemoteGateway) : ICreateNewTaxiUseCase {

    override suspend fun createTaxi(addTaxi: NewTaxiInfo): Taxi {
        return fakeRemoteGateway.createTaxi(addTaxi)
    }

}