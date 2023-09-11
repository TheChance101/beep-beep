package domain.usecase

import domain.entity.InProgressWrapper
import domain.gateway.IRestaurantRemoteGateway

interface IInProgressTrackerUseCase {
    suspend fun getInProgress(): InProgressWrapper
}

class InProgressTrackerUseCase(
    private val remoteGateway: IRestaurantRemoteGateway,
) : IInProgressTrackerUseCase {
    override suspend fun getInProgress(): InProgressWrapper {
        return remoteGateway.getInProgress()
    }
}