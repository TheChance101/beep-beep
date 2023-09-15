package data.remote.fakegateway

import data.remote.mapper.toEntity
import data.remote.model.OrderDto
import domain.entity.Order
import domain.gateway.IMapGateway
import kotlinx.coroutines.delay

class MapFakeGateway : IMapGateway {
    override suspend fun foundNewOrder(): Order {
        delay(3000)
        return OrderDto(
            id = "bsjadahasdy8dsada4ds4a",
            passengerName = "Kamel",
            address = "45, Faisal St., Riyadh, KSA",
        ).toEntity()
    }
}