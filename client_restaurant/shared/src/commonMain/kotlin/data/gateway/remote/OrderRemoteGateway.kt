package data.gateway.remote

import data.remote.mapper.toEntity
import data.remote.mapper.toOrderEntity
import data.remote.model.BaseResponse
import data.remote.model.OrderDto
import domain.entity.Order
import domain.gateway.remote.IOrderRemoteGateway
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import kotlinx.coroutines.flow.Flow


class OrderRemoteGateway(client: HttpClient) : IOrderRemoteGateway,
    BaseRemoteGateway(client = client) {

    override suspend fun getCurrentOrders(restaurantId: String): Flow<Order>? {
        return null
        /*return tryToExecute<BaseResponse<List<OrderDto>>> {

            try {
                ws("/order/restaurant/$restaurantId") {
                    //connect
                    //getCurrentOrders + get active orders
                }
            } catch (e: Exception) {
                //disconnect
                //get active orders
            }

            get("/orders/$restaurantId")
            //todo add pagination
        }.value.toOrderEntity()*/
    }

    override suspend fun getActiveOrders(restaurantId: String): List<Order> {
        return tryToExecute<BaseResponse<List<OrderDto>>> {
            get("/order/$restaurantId/orders")
        }.value?.toOrderEntity() ?: emptyList()
    }

    override suspend fun updateOrderState(orderId: String, orderState: Int): Order {
        return tryToExecute<BaseResponse<OrderDto>>() {
            post("/order/$orderId/status").body()//todo check
        }.value?.toEntity() ?: throw Exception("Error!")
    }

    override suspend fun getOrdersHistory(
        restaurantId: String,
        page: Int,
        limit: Int
    ): List<Order> {
        return tryToExecute<BaseResponse<List<OrderDto>>> {
            get("/order/history/$restaurantId?page=$page&limit=$limit")
        }.value?.toOrderEntity() ?: emptyList()
    }

    //for charts
    override suspend fun getOrdersRevenueByDaysBefore(
        restaurantId: String,
        daysBack: Int
    ): List<Map<Int, Double>> {
        return tryToExecute<BaseResponse<List<Map<Int, Double>>>> {
            get("/order/revenue-by-days-back?restaurantId=$restaurantId&&daysBack=$daysBack")
        }.value ?: emptyList()
    }

    override suspend fun getOrdersCountByDaysBefore(
        restaurantId: String,
        daysBack: Int
    ): List<Map<Int, Int>> {
        return tryToExecute<BaseResponse<List<Map<Int, Int>>>> {
            get("/order/count-by-days-back?restaurantId=$restaurantId&&daysBack=$daysBack")
        }.value ?: emptyList()
    }

}