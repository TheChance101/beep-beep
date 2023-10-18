package domain.dataSource

import domain.entity.Location
import kotlinx.coroutines.flow.Flow

interface IBpLocationDataSource {
    suspend fun getCurrentLocation(): Flow<Location>
    fun stopTracking()
}
