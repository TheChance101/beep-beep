package data.gateway.remote

import data.remote.mapper.toDto
import data.remote.mapper.toEntity
import data.remote.mapper.toOrderEntity
import data.remote.mapper.toTripEntity
import data.remote.model.CartDto
import data.remote.model.OrderDto
import data.remote.model.PaginationResponse
import data.remote.model.ServerResponse
import data.remote.model.TripDto
import domain.entity.Cart
import domain.entity.Order
import domain.entity.Trip
import domain.gateway.IOrderGateway
import domain.utils.GeneralException
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.util.InternalAPI
import kotlinx.serialization.json.Json

class OrderGateway(client: HttpClient) : BaseGateway(client), IOrderGateway {
    override suspend fun getTripHistory(): List<Trip> {
        return tryToExecute<ServerResponse<PaginationResponse<TripDto>>> {
            get("/trip/history")
        }.value?.items?.map { it.toTripEntity() } ?: throw GeneralException.UnknownErrorException
    }

    override suspend fun getOrderHistoryGateway(): List<Order> {
        return tryToExecute<ServerResponse<PaginationResponse<OrderDto>>> {
            get("/orders/user/history")
        }.value?.items?.map { it.toOrderEntity() } ?: throw GeneralException.UnknownErrorException
    }

    override suspend fun getAllCartMeals(): Cart {
        return tryToExecute<ServerResponse<CartDto>> { get("/cart") }.value?.toEntity()
            ?: throw GeneralException.UnknownErrorException
    }

    @OptIn(InternalAPI::class)
    override suspend fun updateCartMeals(cart: Cart) {
        tryToExecute<ServerResponse<CartDto>> {
            put("/cart/replace") {
                body = Json.encodeToString(CartDto.serializer(), cart.toDto())
            }
        }
    }

    override suspend fun orderNow(): Boolean {
        return tryToExecute<ServerResponse<OrderDto>> { put("/cart/orderNow") }.value != null
    }
}

