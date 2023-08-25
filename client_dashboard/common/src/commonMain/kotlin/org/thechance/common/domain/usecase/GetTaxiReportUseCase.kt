package org.thechance.common.domain.usecase

import org.thechance.common.data.remote.gateway.FakeRemoteGateway

interface IGetTaxiReportUseCase {

    suspend fun createTaxiReport()

}

class GetTaxiReportUseCase(
    private val fakeRemoteGateway: FakeRemoteGateway
) : IGetTaxiReportUseCase {
    override suspend fun createTaxiReport() {
        return fakeRemoteGateway.getPdfTaxiReport()
    }
}