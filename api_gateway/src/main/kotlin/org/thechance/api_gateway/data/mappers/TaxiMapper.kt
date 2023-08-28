package org.thechance.api_gateway.data.mappers

import org.thechance.api_gateway.data.model.TaxiResource
import org.thechance.api_gateway.endpoints.model.Taxi

fun TaxiResource.toTaxi(): Taxi {
    return Taxi(
        id = this.id,
        plateNumber = this.plateNumber,
        color = this.color,
        type = this.type,
        driverId = this.driverId,
        isAvailable = this.isAvailable,
        seats = this.seats
    )
}