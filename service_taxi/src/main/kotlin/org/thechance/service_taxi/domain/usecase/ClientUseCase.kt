package org.thechance.service_taxi.domain.usecase

import org.thechance.service_taxi.domain.entity.Taxi
import org.thechance.service_taxi.domain.entity.Trip

interface ClientUseCase {

    suspend fun addTrip(trip: Trip): Boolean

    suspend fun getTripById(tripId: String): Trip

    suspend fun getTaxi(taxiId: String): Taxi?//for rating taxi

     suspend fun rateTrip(tripId: String): Boolean //todo
}