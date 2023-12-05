package data.remote.fakegateway

import data.remote.mapper.toEntity
import data.remote.model.LocationDto
import data.remote.model.TaxiTripDto
import domain.entity.Trip
import domain.gateway.IOrderGateway
import kotlinx.coroutines.delay

class OrderFakeGateway : IOrderGateway {
    override suspend fun findingNewOrder(): Trip {
        delay(4000)
        return TaxiTripDto(
            id = "djsahdjadhjadjas45dsadas",
            clientName = "Cristiano Ronaldo",
            startPoint = LocationDto(
                latitude = 40.6790229,
                longitude = -73.8740306,
            ),
            destination = LocationDto(
                latitude = 30.8859508,
                longitude = 31.4453136,
            ),
            startPointAddress = "ooveofe",
            destinationAddress = "h9viife",
            price = 100.0,
            tripStatus = 1,
        ).toEntity()
    }
}