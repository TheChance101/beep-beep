package domain.usecase

import domain.entity.LinesParameters
import domain.gateway.IFakeRemoteGateway

interface IGetLineChartDataUseCase {
    suspend fun getLineChartData(): LinesParameters
}
class GetLineChartDataUseCase(private val fakeRemoteGateWay: IFakeRemoteGateway)
    :IGetLineChartDataUseCase {
    override suspend fun getLineChartData(): LinesParameters {
        return fakeRemoteGateWay.getLineChartDate()
    }

}