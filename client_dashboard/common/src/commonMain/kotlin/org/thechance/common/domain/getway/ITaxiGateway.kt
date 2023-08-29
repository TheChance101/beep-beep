package org.thechance.common.domain.getway

import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.NewTaxiInfo
import org.thechance.common.domain.entity.Taxi

interface ITaxiGateway {
    suspend fun getTaxis(): DataWrapper<Taxi>
    suspend fun createTaxi(taxi: NewTaxiInfo): Taxi
    suspend fun searchTaxiByDriverUsername(driverUsername: String): DataWrapper<Taxi>
    suspend fun filterTaxis(): DataWrapper<Taxi>
}