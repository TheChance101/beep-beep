package org.thechance.common.data.remote.gateway

import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.NewTaxiInfo
import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.entity.TaxiFiltration
import org.thechance.common.domain.getway.ITaxisGateway

class TaxisGateway : BaseGateway(), ITaxisGateway {

    override suspend fun getTaxis(
        username: String?,
        taxiFiltration: TaxiFiltration,
        page: Int,
        numberOfTaxis: Int
    ): DataWrapper<Taxi> {
        TODO("getTaxis")
    }

    override suspend fun createTaxi(taxi: NewTaxiInfo): Taxi {
        TODO("createTaxi")
    }

    override suspend fun updateTaxi(taxi: NewTaxiInfo): Taxi {
        TODO("updateTaxi")
    }

    override suspend fun deleteTaxi(taxiId: String): Boolean {
        TODO("deleteTaxi")
    }

}