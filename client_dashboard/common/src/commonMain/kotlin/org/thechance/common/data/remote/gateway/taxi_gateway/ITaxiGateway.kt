package org.thechance.common.data.remote.gateway.taxi_gateway

import org.thechance.common.domain.entity.AddTaxi
import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.Taxi

interface ITaxiGateway {
    suspend fun getTaxis(): DataWrapper<Taxi>
    suspend fun createTaxi(taxi: AddTaxi): Taxi
    suspend fun searchTaxiByDriverUsername(driverUsername: String): DataWrapper<Taxi>
}