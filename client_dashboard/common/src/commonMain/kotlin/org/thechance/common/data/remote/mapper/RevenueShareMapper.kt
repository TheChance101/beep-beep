package org.thechance.common.data.remote.mapper

import org.thechance.common.data.remote.model.OrdersRevenueDto
import org.thechance.common.data.remote.model.RevenueShareDto
import org.thechance.common.data.remote.model.TripsRevenueDto
import org.thechance.common.domain.entity.OrdersRevenue
import org.thechance.common.domain.entity.RevenueShare
import org.thechance.common.domain.entity.TripsRevenue

fun RevenueShareDto.toEntity(): RevenueShare {
    return RevenueShare(
            ordersRevenueShare =  ordersRevenueShare.toEntity(),
            tripsRevenueShare = tripsRevenueShare.toEntity()
    )
}

fun TripsRevenueDto.toEntity(): TripsRevenue {
    return TripsRevenue(
            acceptedTrips = acceptedTrips?: 0.0,
            pendingTrips = pendingTrips?: 0.0,
            rejectedTrips = rejectedTrips?: 0.0,
            canceledTrips = canceledTrips?: 0.0,
    )
}

fun OrdersRevenueDto.toEntity(): OrdersRevenue {
    return OrdersRevenue(
            completedOrders = completedOrders?: 0.0,
            canceledOrders = canceledOrders?: 0.0,
            inTheWayOrders = inTheWayOrders?: 0.0,
    )
}