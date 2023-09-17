package org.thechance.common.domain.getway

import org.thechance.common.domain.entity.RevenueShare
import org.thechance.common.domain.entity.TotalRevenueShare
import org.thechance.common.domain.util.RevenueShareDate


interface IRemoteGateway {

    suspend fun getUserData(): String

    suspend fun getPdfTaxiReport()

    suspend fun getCurrentLocation(): String

    suspend fun loginUser(username: String, password: String): Pair<String, String>

    suspend fun getRevenueShare(revenueShareDate: RevenueShareDate): TotalRevenueShare
    suspend fun getDashboardRevenueShare(): RevenueShare

}