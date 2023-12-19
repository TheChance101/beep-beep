package presentation.util.pagesource

import domain.entity.FoodOrder
import domain.entity.PaginationItems
import domain.usecase.IGetTransactionHistoryUseCase
import org.koin.core.component.KoinComponent
import presentation.util.pagesource.BasePagingSource

class FoodOrderPagingSource(
    private val transactionHistory: IGetTransactionHistoryUseCase
) : BasePagingSource<FoodOrder>(), KoinComponent {

    override suspend fun fetchData(page: Int, limit: Int): PaginationItems<FoodOrder> {
        return transactionHistory.getOrdersHistory(page, limit)
    }

}
