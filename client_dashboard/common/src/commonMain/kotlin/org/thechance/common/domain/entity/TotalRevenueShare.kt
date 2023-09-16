package org.thechance.common.domain.entity

data class TotalRevenueShare(
    val earningData: List<Double> ,
    val revenueData: List<Double> ,
    val revenueShare: List<String> ,
)

data class RevenueShare(
    val ordersRevenueShare: OrdersRevenue ,
    val tripsRevenueShare: TripsRevenue,
)

data class TripsRevenue(
    val acceptedTrips: Double,
    val pendingTrips: Double,
    val rejectedTrips: Double,
    val canceledTrips: Double
)

data class OrdersRevenue(
    val completedOrders: Double,
    val canceledOrders: Double,
    val inTheWayOrders: Double,
)
