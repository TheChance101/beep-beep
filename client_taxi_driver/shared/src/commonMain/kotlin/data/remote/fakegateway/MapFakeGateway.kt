package data.remote.fakegateway

import data.remote.mapper.toEntity
import data.remote.model.OrderDto
import domain.gateway.IMapGateway

class MapFakeGateway : IMapGateway {
    override suspend fun findingNewOrder() =
        OrderDto(
            id = "djsahdjadhjadjas45dsadas",
            passengerName = "Kamel Mohamed",
            dropOffAddress = "45, Faisal St., Riyadh, KSA",
            pickUpAddress = "Nirmala,girsls HSS",
        ).toEntity()
    }