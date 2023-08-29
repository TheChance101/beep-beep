package data.gateway.remote

import data.remote.mapper.toEntity
import data.remote.model.BaseResponse
import data.remote.model.OrderDto
import domain.entity.Order
import domain.gateway.remote.IRemoteOrderGateway
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.url
import io.ktor.http.Parameters
import kotlinx.atomicfu.TraceBase.None.append


class RemoteOrderGateway(client: HttpClient) : IRemoteOrderGateway,
    BaseRemoteGateway(client = client) {

    override suspend fun getCurrentOrders(restaurantId: String): List<Order> {
        return emptyList()
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