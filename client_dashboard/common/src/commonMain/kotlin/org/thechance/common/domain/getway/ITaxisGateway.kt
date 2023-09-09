package org.thechance.common.domain.getway

import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.NewTaxiInfo
import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.entity.TaxiFiltration

interface ITaxisGateway {

    suspend fun getTaxis(
        username: String?,
        taxiFiltration: TaxiFiltration,
        page: Int,
        limit: Int
    ): DataWrapper<Taxi>

    suspend fun createTaxi(taxi: NewTaxiInfo): Taxi

    suspend fun updateTaxi(taxi: NewTaxiInfo): Taxi

    suspend fun deleteTaxi(taxiId: String): Boolean

    suspend fun getTaxiById(taxiId: String): Taxi

}