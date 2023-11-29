package data.gateway.remote.pagesource

import domain.entity.PaginationItems
import domain.entity.Trip
import domain.gateway.ITransactionsGateway
import domain.usecase.IGetTransactionHistoryUseCase
import org.koin.core.component.KoinComponent

class TaxiOrderPagingSource(
    private val transaction: IGetTransactionHistoryUseCase,
) : BasePagingSource<Trip>(), KoinComponent {

    override suspend fun fetchData(page: Int, limit: Int): PaginationItems<Trip> {
        return transaction.getTripsHistory(page, limit)
    }
}
