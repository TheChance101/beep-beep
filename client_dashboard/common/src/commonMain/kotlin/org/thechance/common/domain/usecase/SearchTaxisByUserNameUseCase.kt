package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.getway.IRemoteGateway


interface ISearchTaxisByUserNameUseCase {
    suspend fun searchTaxisByUsername(username: String, page:Int, numberOfTaxis:Int): DataWrapper<Taxi>
}

class SearchTaxisByUserNameUseCase(
    private val remoteGateway: IRemoteGateway,
) : ISearchTaxisByUserNameUseCase {
    override suspend fun searchTaxisByUsername(username: String, page:Int, numberOfTaxis:Int): DataWrapper<Taxi> {
        return if (username.isEmpty()) remoteGateway.getTaxis(page,numberOfTaxis)
        else  remoteGateway.searchTaxisByUsername(username,page,numberOfTaxis)
    }

}