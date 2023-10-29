package domain.usecase

import data.remote.fakegateway.OrderFakeGateway
import domain.entity.Order


interface IManageOrderUseCase {
    suspend fun foundNewOrder(): Order
}

class ManageOrderUseCase(
    private val mapGateway: OrderFakeGateway,
) : IManageOrderUseCase {
    override suspend fun foundNewOrder() = mapGateway.findingNewOrder()
}