package data.gateway.remote

import data.remote.mapper.toDto
import data.remote.mapper.toEntity
import data.remote.mapper.toOrderEntity
import data.remote.model.BaseResponse
import data.remote.model.OrderDto
import data.remote.model.PaginationResponse
import data.remote.model.TripDto
import data.remote.model.AddressInfoDto
import domain.entity.AddressInfo
import domain.entity.Order
import domain.entity.PaginationItems
import domain.entity.Trip
import domain.gateway.remote.IOrderRemoteGateway
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.util.InternalAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import presentation.base.UnknownErrorException

class OrderRemoteGateway(client: HttpClient) : IOrderRemoteGateway, BaseRemoteGateway(client) {

    override suspend fun getCurrentOrders(restaurantId: String): Flow<Order> {
        return client.tryToExecuteWebSocket<OrderDto>("/orders/$restaurantId").map { it.toEntity() }
    }

    override suspend fun getActiveOrders(restaurantId: String): List<Order> {
        val result = tryToExecute<BaseResponse<List<OrderDto>>> {
            get("/orders/active/$restaurantId")
        }.value?.toOrderEntity()?: throw UnknownErrorException("")
        println("getActiveOrdersFrom Gateway: ${result}")
        return result
    }
    @OptIn(InternalAPI::class)
    override suspend fun createOrderTrip(trip: Trip): Trip {
        val tripDto = trip.toDto()
        return tryToExecute<BaseResponse<TripDto>> {
            post("/trip/delivery"){ body = Json.encodeToString(TripDto.serializer(), tripDto) }
        }.value?.toEntity()?: throw UnknownErrorException("")
    }

    override suspend fun updateOrderState(orderId: String): Order {
        val result = tryToExecute<BaseResponse<OrderDto>>() {
            put("/order/$orderId")
        }.value?.toEntity() ?: throw Exception("Error!")
        println("updateOrderStateFrom Gateway: ${result}")
        return result
    }
    override suspend fun getAddressInfo(userId: String): AddressInfo {
        val result = tryToExecute<BaseResponse<AddressInfoDto>> {
            put("/user/address/$userId")
        }.value?.toEntity() ?: throw Exception("Error!")
        println("getUserLocation Gateway: ${result}")
        return result
    }
    override suspend fun cancelOrder(orderId: String): Order {
        return tryToExecute<BaseResponse<OrderDto>>() {
            put("order/cancel/$orderId")
        }.value?.toEntity() ?: throw Exception("Error!")
    }

    override suspend fun getOrdersHistory(
        restaurantId: String,
        page: Int,
        limit: Int,
    ): PaginationItems<Order> {
        val result = tryToExecute<BaseResponse<PaginationResponse<OrderDto>>> {
            get("/orders/restaurant/$restaurantId/history?page=$page&limit=$limit")
        }.value
        return paginateData(
            result = result?.items?.map { it.toEntity() } ?: throw UnknownErrorException(""),
            page = result.page,
            total = result.total)
    }

    override suspend fun getOrdersRevenueByDaysBefore(
        restaurantId: String, daysBack: Int,
    ): List<Map<String, Double>> {
        val result = tryToExecute<BaseResponse<List<Map<String, Double>>>> {
            get("/orders/$restaurantId/revenue-by-days-back")
        }.value ?: emptyList()
        return result
    }

    override suspend fun getOrdersCountByDaysBefore(
        restaurantId: String, daysBack: Int,
    ): List<Map<String, Int>> {
        val result = tryToExecute<BaseResponse<List<Map<String, Int>>>> {
            get("/orders/$restaurantId/count-by-days-back")
        }.value ?: emptyList()
        return result
    }
}
