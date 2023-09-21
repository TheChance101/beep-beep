package domain.gateway

import kotlinx.coroutines.flow.Flow

interface IBpLocationDataSource {
    suspend fun getCurrentLocation(): Flow<Pair<Double, Double>>
    fun stopTracking()
}