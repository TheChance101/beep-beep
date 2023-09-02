package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.NewTaxiInfo
import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.getway.IRemoteGateway


interface IManageTaxisUseCase {
    suspend fun createTaxi(addTaxi: NewTaxiInfo): Taxi
    suspend fun createTaxiReport()
    suspend fun getTaxis(page:Int,numberOfUsers: Int): DataWrapper<Taxi>
    suspend fun updateTaxi(addTaxi: NewTaxiInfo): Boolean
    suspend fun deleteTaxi(taxiId: String): Boolean


}
class ManageTaxisUseCase(
    private val remoteGateway: IRemoteGateway

): IManageTaxisUseCase {
    override suspend fun createTaxi(addTaxi: NewTaxiInfo): Taxi {
        return remoteGateway.createTaxi(addTaxi)
    }

    override suspend fun createTaxiReport() {
        return remoteGateway.getPdfTaxiReport()
    }

    override suspend fun getTaxis(page:Int,numberOfUsers: Int): DataWrapper<Taxi> {
        return remoteGateway.getTaxis(page,numberOfUsers)
    }
    override suspend fun deleteTaxi(taxiId: String): Boolean {
        return remoteGateway.deleteTaxi(taxiId)
    }
    override suspend fun updateTaxi(addTaxi: NewTaxiInfo): Boolean {
        return remoteGateway.updateTaxi(addTaxi)
    }
}