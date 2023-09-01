package org.thechance.common.data.remote.gateway

import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.NewTaxiInfo
import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.getway.ITaxiGateway

class TaxiGateway:BaseGateway(), ITaxiGateway {
    override suspend fun getTaxis(): DataWrapper<Taxi> {
        TODO("getTaxis")
    }

    override suspend fun createTaxi(taxi: NewTaxiInfo): Taxi {
        TODO("createTaxi")
    }

    override suspend fun updateTaxi(taxi: NewTaxiInfo): Boolean {
        TODO("updateTaxi")
    }

    override suspend fun deleteTaxi(taxiId: String): Boolean {
        TODO("deleteTaxi")
    }

    override suspend fun searchTaxiByDriverUsername(driverUsername: String): DataWrapper<Taxi> {
        TODO("searchTaxiByDriverUsername")
    }

    override suspend fun filterTaxis(): DataWrapper<Taxi> {
        TODO("filterTaxis")
    }
}