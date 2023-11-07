package data.gateway.remote.pagesource

import domain.entity.FoodOrder
import domain.entity.PaginationItems
import domain.gateway.ITransactionsGateway
import org.koin.core.component.KoinComponent

class FoodOrderPagingSource(
    private val transactionsGateway: ITransactionsGateway,
) : BasePagingSource<FoodOrder>(), KoinComponent {

    override suspend fun fetchData(page: Int, limit: Int): PaginationItems<FoodOrder> {
        return transactionsGateway.getOrderHistoryGateway(page, limit)
    }

}
