package data.gateway.remote.pagesource

import domain.entity.Order
import domain.entity.PaginationItems
import domain.gateway.remote.IOrderRemoteGateway
import kotlin.properties.Delegates

class OrdersHistoryPagingSource(
    private val remoteGateway: IOrderRemoteGateway,
) : BasePagingSource<Order>() {
    private var restaurantId by Delegates.notNull<String>()

    fun initOrders(id: String) {
        restaurantId = id
    }

    override suspend fun fetchData(page: Int, limit: Int): PaginationItems<Order> {
        return remoteGateway.getOrdersHistory(restaurantId, page, limit)
    }

}
