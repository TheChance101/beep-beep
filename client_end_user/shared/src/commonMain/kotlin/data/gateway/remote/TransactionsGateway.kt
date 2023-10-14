package data.gateway.remote

import data.remote.mapper.toCuisineEntity
import data.remote.mapper.toEntity
import data.remote.model.CartDto
import data.remote.model.CuisineDto
import data.remote.model.OrderDto
import data.remote.model.PaginationResponse
import data.remote.model.ServerResponse
import domain.entity.Cart
import domain.entity.Order
import domain.entity.Trip
import domain.gateway.ITransactionsGateway
import domain.utils.GeneralException
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class TransactionsGateway(client: HttpClient) : BaseGateway(client = client), ITransactionsGateway {
    override suspend fun getTripHistory(): List<Trip> {
        //TODO("Not yet implemented")
        return emptyList()
    }

    override suspend fun getOrderHistoryGateway(): List<Order> {
        return tryToExecute<ServerResponse<PaginationResponse<OrderDto>>> {
            get("orders/user/history")
        }.value?.items?.toEntity() ?: throw GeneralException.NotFoundException
    }

    override suspend fun getCart(): Cart {
        return tryToExecute<ServerResponse<CartDto>> {
            get("/cart")
        }.value?.toEntity() ?: throw GeneralException.NotFoundException
    }
}
