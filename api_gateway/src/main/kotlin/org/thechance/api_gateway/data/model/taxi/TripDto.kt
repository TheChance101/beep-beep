package org.thechance.api_gateway.data.model.taxi

import kotlinx.serialization.Serializable
import org.thechance.api_gateway.data.model.LocationDto
import org.thechance.api_gateway.data.model.identity.UserDetailsDto
import org.thechance.api_gateway.data.model.restaurant.OrderDto
import org.thechance.api_gateway.data.model.restaurant.RestaurantDto

@Serializable
data class TripDto(
    val id: String? = null,
    val taxiId: String? = null,
    val driverId: String? = null,
    val clientId: String? = null,
    val taxiPlateNumber: String? = null,
    val taxiDriverName: String? = null,
    val startPoint: LocationDto? = null,
    val destination: LocationDto? = null,
    val startPointAddress: String? = null,
    val destinationAddress: String? = null,
    val rate: Double? = null,
    val price: Double? = null,
    val startDate: String? = null,
    val endDate: String? = null,
    val isATaxiTrip: Boolean? = null,
    val tripStatus: Int = 0
)

fun TripDto.toTaxiTripResponse(userInfo: UserDetailsDto): TaxiTripResponse {
    return TaxiTripResponse(
        id = id ?: "",
        clientName = userInfo.fullName,
        startPoint = startPoint ?: LocationDto(0.0, 0.0),
        destination = destination ?: LocationDto(0.0, 0.0),
        startPointAddress = startPointAddress ?: "",
        destinationAddress = destinationAddress ?: "",
        price = price ?: 0.0,
        tripStatus = tripStatus
    )
}

fun TripDto.toDeliveryTripResponse(order: OrderDto): DeliveryTripResponse {
    return DeliveryTripResponse(
        id = id ?: "",
        restaurantName = order.restaurantName ?: "",
        restaurantImage = order.restaurantImage ?: "",
        startPoint = startPoint ?: LocationDto(0.0, 0.0),
        destination = destination ?: LocationDto(0.0, 0.0),
        startPointAddress = startPointAddress ?: "",
        destinationAddress = destinationAddress ?: "",
        price = price ?: 0.0,
        tripStatus = tripStatus
    )
}

fun TripDto.toRideTrackingResponse(taxi: TaxiDto): RideTrackingResponse {
    return RideTrackingResponse(
        id = id ?: "",
        taxiPlateNumber = taxiPlateNumber ?: "",
        taxiDriverName = taxi.driverUsername,
        driverImage = taxi.driverImage ?: "",
        rate = taxi.rate ?: 0.0,
        carType = taxi.type,
        startPoint = startPoint ?: LocationDto(0.0, 0.0),
        destination = destination ?: LocationDto(0.0, 0.0),
        startPointAddress = startPointAddress ?: "",
        destinationAddress = destinationAddress ?: "",
        tripStatus = tripStatus
    )
}

fun TripDto.toDeliveryTrackingResponse(): DeliveryTrackingResponse {
    return DeliveryTrackingResponse(
        id = id ?: "",
        startPoint = startPoint ?: LocationDto(0.0, 0.0),
        destination = destination ?: LocationDto(0.0, 0.0),
        status = tripStatus
    )
}