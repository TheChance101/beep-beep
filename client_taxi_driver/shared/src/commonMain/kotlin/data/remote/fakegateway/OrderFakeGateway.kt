package data.remote.fakegateway

import data.remote.mapper.toEntity
import data.remote.model.LocationDto
import data.remote.model.OrderDto
import domain.entity.Order
import domain.gateway.IOrderGateway
import kotlinx.coroutines.delay

class OrderFakeGateway : IOrderGateway {
    override suspend fun findingNewOrder(): Order {
        delay(4000)
        return OrderDto(
            id = "djsahdjadhjadjas45dsadas",
            passengerId = "sjdadjsadsa-dsa4d8sa4dsa",
            passengerName = "Cristiano Ronaldo",
            pickUpAddress = LocationDto(
                lat = 40.6790229,
                lng = -73.8740306,
                addressName = "45, Faisal St., Riyadh, KSA",
            ),
            dropOffAddress = LocationDto(
                lat = 30.8859508,
                lng = 31.4453136,
                addressName = "Nirmala,girsls HSS",
            ),
        ).toEntity()
    }
}