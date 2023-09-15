package domain.usecase

import domain.entity.BarsParameters
import domain.gateway.IFakeRemoteGateway

interface IGetBarChartDataUseCase {
    suspend fun getBarChartData(): BarsParameters
}
class GetBarChartDataUseCase(private val fakeRemoteGateWay: IFakeRemoteGateway)
    : IGetBarChartDataUseCase {
    override suspend fun getBarChartData(): BarsParameters {
        return fakeRemoteGateWay.getBarChartDate()
    }

}