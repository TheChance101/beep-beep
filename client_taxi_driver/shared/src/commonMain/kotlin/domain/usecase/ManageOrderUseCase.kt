package domain.usecase

import data.remote.fakegateway.MapFakeGateway
import domain.entity.Order
import kotlinx.coroutines.delay


interface IManageOrderUseCase {
    suspend fun foundNewOrder(): Order
}

class ManageOrderUseCase(
    private val fakeGateway: MapFakeGateway,
) : IManageOrderUseCase {
    override suspend fun foundNewOrder(): Order {
        delay(3000)
        return fakeGateway.findingNewOrder()
    }
}