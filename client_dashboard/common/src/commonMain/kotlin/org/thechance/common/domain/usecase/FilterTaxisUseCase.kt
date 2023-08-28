package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.entity.TaxiFiltered
import org.thechance.common.domain.getway.IRemoteGateway

interface IFilterTaxisUseCase {
    suspend operator fun invoke(taxi:TaxiFiltered, page: Int, numberOfTaxis: Int):DataWrapper<Taxi>
}

class FilterTaxisUseCase(
    private val remoteGateway: IRemoteGateway,
) : IFilterTaxisUseCase {
    override suspend operator fun invoke(taxi:TaxiFiltered, page: Int, numberOfTaxis: Int):DataWrapper<Taxi> {
        return remoteGateway.filterTaxis(taxi = taxi,page = page, numberOfTaxis = numberOfTaxis)
    }
}