package data.gateway.remote

import data.remote.mapper.toEntity
import data.remote.mapper.toOrderEntity
import data.remote.model.BaseResponse
import data.remote.model.OrderDto
import domain.entity.Order
import domain.gateway.remote.IOrderRemoteGateway
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.ws
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.http.Parameters
import kotlinx.atomicfu.TraceBase.None.append


class OrderRemoteGateway(client: HttpClient) : IOrderRemoteGateway,
    BaseRemoteGateway(client = client) {

    override suspend fun getCurrentOrders(restaurantId: String): List<Order>? {
        return tryToExecute<BaseResponse<List<OrderDto>>> {

            try {
                ws("orders") {
                    //connect
                    //getCurrentOrders + get active orders
                }
            } catch (e: Exception) {
                //disconnect
                //get active orders
            }

            get("/orders/$restaurantId")
            //todo add pagination
        }.value?.toOrderEntity()
    }

    override suspend fun updateOrderState(orderId: String, orderState: Int): Order? {
        return tryToExecute<BaseResponse<OrderDto>>() {
            submitForm(
                formParameters = Parameters.build {
                    append("orderId", orderId)
                    append("orderState", orderState)
                }
            ) {
                url("/order/$orderId/status")
            }
        }.value?.toEntity()
    }
    override suspend fun getOrderById(orderId: String): Order? {
        return null
    }
    override suspend fun getOrdersHistory(restaurantId: String): List<Order> {
        return emptyList()
    }

}