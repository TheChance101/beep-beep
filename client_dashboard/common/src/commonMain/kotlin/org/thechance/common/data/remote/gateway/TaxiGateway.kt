package org.thechance.common.data.remote.gateway

import org.thechance.common.data.remote.gateway.BaseGateway
import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.NewTaxiInfo
import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.getway.ITaxiGateway

class TaxiGateway: BaseGateway(), ITaxiGateway {
    override suspend fun getTaxis(): DataWrapper<Taxi> {
        TODO("Not yet implemented")
    }

    override suspend fun createTaxi(taxi: NewTaxiInfo): Taxi {
        TODO("Not yet implemented")
    }

    override suspend fun searchTaxiByDriverUsername(driverUsername: String): DataWrapper<Taxi> {
        TODO("Not yet implemented")
    }

    override suspend fun filterTaxis(): DataWrapper<Taxi> {
        TODO("Not yet implemented")
    }
}