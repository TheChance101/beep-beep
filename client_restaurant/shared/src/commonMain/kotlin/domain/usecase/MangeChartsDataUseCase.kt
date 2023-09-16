package domain.usecase

import domain.entity.BarsParameters
import domain.entity.LinesParameters
import domain.gateway.IFakeRemoteGateway

interface IMangeChartsUseCase {
    suspend fun getLineChartData(): LinesParameters
    suspend fun getBarChartData(): BarsParameters
}
class MangeChartsUseCase(private val fakeRemoteGateWay: IFakeRemoteGateway)
    :IMangeChartsUseCase {
    override suspend fun getLineChartData(): LinesParameters {
        return fakeRemoteGateWay.getLineChartDate()
    }
    override suspend fun getBarChartData(): BarsParameters {
        return fakeRemoteGateWay.getBarChartDate()
    }

}