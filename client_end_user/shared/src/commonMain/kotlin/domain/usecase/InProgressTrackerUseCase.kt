package domain.usecase

import domain.entity.InProgressWrapper
import domain.gateway.IRestaurantGateway

interface IInProgressTrackerUseCase {
    suspend fun getInProgress(): InProgressWrapper
}

class InProgressTrackerUseCase(
    private val remoteGateway: IRestaurantGateway,
) : IInProgressTrackerUseCase {
    override suspend fun getInProgress(): InProgressWrapper {
        return remoteGateway.getInProgress()
    }
}