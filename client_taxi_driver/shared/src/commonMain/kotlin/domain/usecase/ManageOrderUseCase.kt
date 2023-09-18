package domain.usecase

import data.remote.fakegateway.MapFakeGateway
import domain.entity.Order


interface IManageOrderUseCase {
    suspend fun foundNewOrder(): Order
}

class ManageOrderUseCase(
    private val fakeGateway: MapFakeGateway,
) : IManageOrderUseCase {
    override suspend fun foundNewOrder() = fakeGateway.findingNewOrder()
}