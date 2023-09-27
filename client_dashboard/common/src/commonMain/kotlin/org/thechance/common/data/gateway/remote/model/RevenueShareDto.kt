package org.thechance.common.data.gateway.remote.model

import kotlinx.serialization.SerialName

data class RevenueShareDto(
    @SerialName("ordersRevenueShare")
    val ordersRevenueShare: OrdersRevenueDto,
    @SerialName("tripsRevenueShare")
    val tripsRevenueShare: TripsRevenueDto,
)
data class TripsRevenueDto(
    @SerialName("acceptedTrips")
    val acceptedTrips: Double?,
    @SerialName("pendingTrips")
    val pendingTrips: Double?,
    @SerialName("rejectedTrips")
    val rejectedTrips: Double?,
    @SerialName("canceledTrips")
    val canceledTrips: Double?
)

data class OrdersRevenueDto(
    @SerialName("completedOrders")
    val completedOrders: Double?,
    @SerialName("canceledOrders")
    val canceledOrders: Double?,
    @SerialName("inTheWayOrders")
    val inTheWayOrders: Double?,
)