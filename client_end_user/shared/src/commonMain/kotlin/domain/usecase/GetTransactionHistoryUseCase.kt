package domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import data.gateway.remote.pagesource.FoodOrderPagingSource
import data.gateway.remote.pagesource.TaxiOrderPagingSource
import domain.entity.FoodOrder
import domain.entity.Trip
import domain.gateway.ITransactionsGateway
import kotlinx.coroutines.flow.Flow

interface IGetTransactionHistoryUseCase {
    suspend fun getOrdersHistory(): Flow<PagingData<FoodOrder>>
    suspend fun getTripsHistory(): Flow<PagingData<Trip>>
}

class GetTransactionHistoryUseCase(
    private val taxiOrder: TaxiOrderPagingSource,
    private val foodOrderDataSource: FoodOrderPagingSource,
) : IGetTransactionHistoryUseCase {

    override suspend fun getOrdersHistory(): Flow<PagingData<FoodOrder>> {
        return Pager(config = PagingConfig(pageSize = 10, enablePlaceholders = true),
            pagingSourceFactory = { foodOrderDataSource }
        ).flow
    }

    override suspend fun getTripsHistory(): Flow<PagingData<Trip>> {
        return Pager(config = PagingConfig(pageSize = 10, enablePlaceholders = true),
            pagingSourceFactory = { taxiOrder }
        ).flow
    }
}
