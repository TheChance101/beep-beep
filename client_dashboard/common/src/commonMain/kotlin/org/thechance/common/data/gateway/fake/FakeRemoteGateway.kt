package org.thechance.common.data.gateway.fake


import org.thechance.common.data.gateway.remote.mapper.toEntity
import org.thechance.common.data.gateway.remote.model.OrdersRevenueDto
import org.thechance.common.data.gateway.remote.model.RevenueShareDto
import org.thechance.common.data.gateway.remote.model.TripsRevenueDto
import org.thechance.common.domain.entity.RevenueShare
import org.thechance.common.domain.entity.TotalRevenueShare
import org.thechance.common.domain.getway.IRemoteGateway
import org.thechance.common.domain.util.RevenueShareDate

class FakeRemoteGateway() : IRemoteGateway {

    override suspend fun getRevenueShare(revenueShareDate: RevenueShareDate): TotalRevenueShare {
        return when (revenueShareDate.value) {
            0 -> TotalRevenueShare(
                    revenueData = listOf(
                            50.0,
                            30.6,
                            77.0,
                            69.6,
                            50.0,
                            30.6,
                            80.0,
                            50.6,
                            44.0,
                            100.6,
                            10.0,
                            50.0
                    ),
                    earningData = listOf(
                            0.6,
                            10.6,
                            80.0,
                            50.6,
                            44.0,
                            100.6,
                            10.0,
                            50.0,
                            30.6,
                            77.0,
                            69.6,
                            50.0
                    ),
                    revenueShare = listOf(
                            "Jan",
                            "Feb",
                            "Mar",
                            "Apr",
                            "May",
                            "Jun",
                            "Jul",
                            "Aug",
                            "Sep",
                            "Oct",
                            "Nov",
                            "Dec"
                    )
            )

            1 -> TotalRevenueShare(
                    revenueData = listOf(
                            99.6,
                            15.0,
                            30.6,
                            14.0,
                            100.6,
                            10.0,
                            50.0,
                            63.3,
                            12.3,
                            22.2,
                            12.4
                    ),
                    earningData = listOf(
                            10.0,
                            50.0,
                            0.6,
                            10.6,
                            80.0,
                            77.0,
                            69.6,
                            0.0,
                            1.3,
                            22.2,
                            12.4
                    ),
                    revenueShare = listOf(
                            "13 Wed",
                            "14 Thu",
                            "15 Fri",
                            "16 Sat",
                            "17 Sun",
                            "18 Mon",
                            "19 Tue",
                            "20 Wed",
                            "21 Thu",
                            "22 Fri",
                            "23 Sat"
                    )
            )

            2 -> TotalRevenueShare(
                    revenueData = listOf(
                            100.0,
                            50.6,
                            24.0,
                            91.6,
                            50.0,
                            30.6,
                            80.0,
                            50.6,
                            44.0,
                            100.6,
                            10.0,
                            50.0,
                            21.2,
                            43.2
                    ),
                    earningData = listOf(
                            0.6,
                            10.6,
                            80.0,
                            50.6,
                            44.0,
                            100.6,
                            10.0,
                            50.0,
                            30.6,
                            77.0,
                            69.6,
                            50.0,
                            21.2,
                            43.2
                    ),
                    revenueShare = listOf(
                            "Week 1",
                            "Week 2",
                            "Week 3",
                            "Week 4",
                            "Week 5",
                            "Week 6",
                            "Week 7",
                            "Week 8",
                            "Week 9",
                            "Week 10",
                            "Week 11",
                            "Week 12",
                            "Week 13",
                            "Week 14"
                    )
            )

            else -> throw UnknownError()
        }

    }

    override suspend fun getDashboardRevenueShare(): RevenueShare {
        return RevenueShareDto(
                ordersRevenueShare = OrdersRevenueDto(
                        completedOrders = 100.0,
                        canceledOrders = 50.3,
                        inTheWayOrders = 30.0,
                ),
                tripsRevenueShare = TripsRevenueDto(
                        acceptedTrips = 100.0,
                        pendingTrips = 50.3,
                        rejectedTrips = 10.0,
                        canceledTrips = 30.0,
                )
        ).toEntity()
    }

    override suspend fun getCurrentLocation(): String {
        return "30.044420,31.235712"
    }

}