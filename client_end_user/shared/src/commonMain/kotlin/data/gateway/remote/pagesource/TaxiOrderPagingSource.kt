package data.gateway.remote.pagesource

import domain.entity.PaginationItems
import domain.entity.Trip
import domain.gateway.ITransactionsGateway
import org.koin.core.component.KoinComponent

class TaxiOrderPagingSource(
    private val transactionsGateway: ITransactionsGateway,
) : BasePagingSource<Trip>(), KoinComponent {

    override suspend fun fetchData(page: Int, limit: Int): PaginationItems<Trip> {
        return transactionsGateway.getTripHistory(page, limit)
    }

}
