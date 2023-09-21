package domain.gateway

import kotlinx.coroutines.flow.Flow

interface ILocationDataSource {
    suspend fun getCurrentLocation(): Flow<Pair<Double, Double>>
    fun stopTracking()
}