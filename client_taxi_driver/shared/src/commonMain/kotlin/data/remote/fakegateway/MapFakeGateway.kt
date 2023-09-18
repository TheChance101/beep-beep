package data.remote.fakegateway

import data.remote.mapper.toEntity
import data.remote.model.OrderDto
import domain.entity.Order
import domain.gateway.IMapGateway
import kotlinx.coroutines.delay

class MapFakeGateway : IMapGateway {
    override suspend fun findingNewOrder(): Order {
        delay(3000)
        return OrderDto(
            id = "djsahdjadhjadjas45dsadas",
            passengerId = "sjdadjsadsa-dsa4d8sa4dsa",
            passengerName = "Kamel Mohamed",
            dropOffAddress = "45, Faisal St., Riyadh, KSA",
            pickUpAddress = "Nirmala,girsls HSS",
        ).toEntity()
    }
}