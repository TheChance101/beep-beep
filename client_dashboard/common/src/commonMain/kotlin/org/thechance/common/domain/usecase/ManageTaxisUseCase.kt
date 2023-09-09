package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.NewTaxiInfo
import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.entity.TaxiFiltration
import org.thechance.common.domain.getway.IRemoteGateway
import org.thechance.common.domain.getway.ITaxisGateway


interface IManageTaxisUseCase {

    suspend fun createTaxi(addTaxi: NewTaxiInfo): Taxi

    suspend fun createTaxiReport()

    suspend fun getTaxis(
        username: String? = null,
        taxiFiltration: TaxiFiltration,
        page: Int,
        limit: Int
    ): DataWrapper<Taxi>

    suspend fun updateTaxi(addTaxi: NewTaxiInfo): Taxi

    suspend fun deleteTaxi(taxiId: String): Boolean

    suspend fun getTaxiById(id: String): Taxi

}

class ManageTaxisUseCase(
    private val taxiGateway: ITaxisGateway,
    private val remoteGateway: IRemoteGateway
) : IManageTaxisUseCase {

    override suspend fun createTaxi(addTaxi: NewTaxiInfo): Taxi {
        return taxiGateway.createTaxi(addTaxi)
    }

    override suspend fun createTaxiReport() {
        return remoteGateway.getPdfTaxiReport()
    }

    override suspend fun getTaxis(
        username: String?,
        taxiFiltration: TaxiFiltration,
        page: Int,
        limit: Int
    ): DataWrapper<Taxi> {
        return taxiGateway.getTaxis(
            page = page,
            limit = limit,
            username = username,
            taxiFiltration = taxiFiltration
        )
    }

    override suspend fun deleteTaxi(taxiId: String): Boolean {
        return taxiGateway.deleteTaxi(taxiId)
    }

    override suspend fun getTaxiById(id: String): Taxi {
        return taxiGateway.getTaxiById(id)
    }

    override suspend fun updateTaxi(addTaxi: NewTaxiInfo): Taxi {
        return taxiGateway.updateTaxi(addTaxi)
    }

}