package domain.usecase

import domain.entity.Statistics
import domain.gateway.IFakeRemoteGateway

interface IMangeStatisticsUseCase {
    suspend fun getStatistics(): Statistics
}
class MangeStatisticsUseCase(private val fakeRemoteGateWay: IFakeRemoteGateway)
    :IMangeStatisticsUseCase {
    override suspend fun getStatistics(): Statistics {
        return fakeRemoteGateWay.getStatistics()
    }


}