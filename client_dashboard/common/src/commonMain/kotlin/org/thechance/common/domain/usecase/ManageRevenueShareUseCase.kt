package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.RevenueShare
import org.thechance.common.domain.entity.TotalRevenueShare
import org.thechance.common.domain.getway.IRemoteGateway
import org.thechance.common.domain.util.RevenueShareDate


interface IManageRevenueShareUseCase {
   suspend fun getRevenueShare(revenueShareDate: RevenueShareDate): TotalRevenueShare
   suspend fun getDashboardRevenueShare(): RevenueShare
}
class ManageRevenueShareUseCase(
        private val remoteGateway: IRemoteGateway
):IManageRevenueShareUseCase {
   override suspend fun getRevenueShare(revenueShareDate: RevenueShareDate): TotalRevenueShare {
       val type = when (revenueShareDate) {
           RevenueShareDate.MONTHLY -> 0
           RevenueShareDate.WEEKLY -> 1
           RevenueShareDate.DAILY -> 2
       }
       return remoteGateway.getRevenueShare(type)
   }
    override suspend fun getDashboardRevenueShare(): RevenueShare {
        return remoteGateway.getDashboardRevenueShare()
    }


}