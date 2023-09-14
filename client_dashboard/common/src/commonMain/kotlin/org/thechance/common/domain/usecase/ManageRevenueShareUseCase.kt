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
       return remoteGateway.getRevenueShare(revenueShareDate)
   }
    override suspend fun getDashboardRevenueShare(): RevenueShare {
        return remoteGateway.getDashboardRevenueShare()
    }


}