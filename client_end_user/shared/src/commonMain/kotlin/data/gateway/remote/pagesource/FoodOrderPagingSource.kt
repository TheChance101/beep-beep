package data.gateway.remote.pagesource

import domain.entity.FoodOrder
import domain.entity.PaginationItems
import domain.usecase.IGetTransactionHistoryUseCase
import org.koin.core.component.KoinComponent

class FoodOrderPagingSource(
    private val transactionHistory: IGetTransactionHistoryUseCase
) : BasePagingSource<FoodOrder>(), KoinComponent {

    override suspend fun fetchData(page: Int, limit: Int): PaginationItems<FoodOrder> {
        return transactionHistory.getOrdersHistory(page, limit)
    }

}
