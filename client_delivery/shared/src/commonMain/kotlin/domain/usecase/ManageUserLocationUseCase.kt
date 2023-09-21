package domain.usecase

import domain.entity.Location
import domain.gateway.local.ILocationGateway
import kotlinx.coroutines.flow.Flow

interface IManageUserLocationUseCase {
    suspend fun startTracking()
}

class ManageUserLocationUseCase(private val locationGateway: ILocationGateway) :
    IManageUserLocationUseCase {
    override suspend fun startTracking() {
        locationGateway.startTracking()
    }

}