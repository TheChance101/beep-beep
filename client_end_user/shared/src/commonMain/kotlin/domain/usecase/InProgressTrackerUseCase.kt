package domain.usecase

import domain.entity.Order
import domain.entity.Taxi
import domain.gateway.IRestaurantRemoteGateway

interface IInProgressTrackerUseCase {
    suspend fun getTaxiOnTheWay(): Taxi?
    suspend fun getActiveRide(): Taxi?
    suspend fun getActiveOrder(): Order?
}

class InProgressTrackerUseCase(
    private val remoteGateway: IRestaurantRemoteGateway,
) : IInProgressTrackerUseCase {
    override suspend fun getTaxiOnTheWay(): Taxi? {
        return remoteGateway.getTaxiOnTheWay()
    }

    override suspend fun getActiveRide(): Taxi? {
        return remoteGateway.getActiveRide()
    }

    override suspend fun getActiveOrder(): Order? {
        return remoteGateway.getActiveOrder()
    }

}