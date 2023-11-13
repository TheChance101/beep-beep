package data.gateway.remote

import data.remote.mapper.toEntity
import data.remote.mapper.toOrderEntity
import data.remote.model.BaseResponse
import data.remote.model.OrderDto
import data.remote.model.PaginationResponse
import domain.entity.Order
import domain.entity.PaginationItems
import domain.gateway.remote.IOrderRemoteGateway
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import presentation.base.UnknownErrorException


class OrderRemoteGateway(client: HttpClient) : IOrderRemoteGateway,
    BaseRemoteGateway(client = client) {

    override suspend fun getCurrentOrders(restaurantId: String): Flow<Order> {
        return  client.tryToExecuteWebSocket<OrderDto>("/orders/$restaurantId").map { it.toEntity() }
    }

    override suspend fun getActiveOrders(restaurantId: String): List<Order> {
        return tryToExecute<BaseResponse<List<OrderDto>>> {
            get("/order/$restaurantId/orders")
        }.value?.toOrderEntity() ?: emptyList()
    }

    override suspend fun updateOrderState(orderId: String): Order {
        return tryToExecute<BaseResponse<OrderDto>>() {
            post("/order/$orderId")
        }.value?.toEntity() ?: throw Exception("Error!")
    }

    override suspend fun getOrdersHistory(
        restaurantId: String,
        page: Int,
        limit: Int
    ): PaginationItems<Order> {
        val result= tryToExecute<BaseResponse<PaginationResponse<OrderDto>>> {
            get("/orders/restaurant/$restaurantId/history?page=$page&limit=$limit")
        }.value
        println("getOrdersHistoryFrom Gateway: ${result}")
         return paginateData(
            result = result?.items?.map { it.toEntity() } ?: throw UnknownErrorException(""),
            page= result.page,
            total = result.total)
    }

    //for charts
    override suspend fun getOrdersRevenueByDaysBefore(
        restaurantId: String,
        daysBack: Int,
    ): List<Map<String, Double>> {
        val result = tryToExecute<BaseResponse<List<Map<String, Double>>>> {
            submitForm(
                url = ("/orders/$restaurantId/revenue-by-days-back"),
                formParameters = Parameters.build {
                    append("daysBack", daysBack.toString())
                },
                block = { method = HttpMethod.Get }
            )
        }.value ?: emptyList()
        return result
    }

    override suspend fun getOrdersCountByDaysBefore(
        restaurantId: String,
        daysBack: Int,
    ): List<Map<String, Int>> {
        val result = tryToExecute<BaseResponse<List<Map<String, Int>>>> {
            submitForm(
                url = ("/orders/$restaurantId/count-by-days-back"),
                formParameters = Parameters.build {
                    append("daysBack", daysBack.toString())
                },
                block = { method = HttpMethod.Get }
            )
        }.value ?: emptyList()
        return result
    }
}