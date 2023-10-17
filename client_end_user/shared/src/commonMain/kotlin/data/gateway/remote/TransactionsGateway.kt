package data.gateway.remote

import data.remote.mapper.toEntity
import data.remote.mapper.toTripEntity
import data.remote.model.CartDto
import data.remote.model.OrderDto
import data.remote.model.PaginationResponse
import data.remote.model.ServerResponse
import data.remote.model.TripDto
import domain.entity.Cart
import domain.entity.FoodOrder
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

    override suspend fun getOrderHistoryGateway(): List<FoodOrder> {
        return tryToExecute<ServerResponse<PaginationResponse<OrderDto>>> {
            get("orders/user/history")
        }.value?.items?.toEntity() ?: throw GeneralException.NotFoundException
    }

    override suspend fun getCart(): Cart {
        return tryToExecute<ServerResponse<CartDto>> {
            get("/cart")
        }.value?.toEntity() ?: throw GeneralException.NotFoundException
    }

    override suspend fun getActiveTrips(): List<Trip> {
        val response = tryToExecute<ServerResponse<List<TripDto>>> {
            get("active/trips")
        }
        if (response.isSuccess) {
            return response.value?.map { it.toTripEntity() }
                ?: throw GeneralException.NotFoundException
        } else {
            // here we can handle different errors by checking response.status.code
            // and also we can use the message sent from the server to pass it throw the exception
            // and show it to user if we want
            if (response.status.code == 404) {
                throw GeneralException.NotFoundException
            } else {
                throw GeneralException.UnknownErrorException
            }
        }
    }

    override suspend fun getActiveOrders(): List<FoodOrder> {
        val response = tryToExecute<ServerResponse<List<OrderDto>>> {
            get("active/orders")
        }
        if (response.isSuccess) {
            return response.value?.toEntity() ?: throw GeneralException.NotFoundException
        } else {
            // here we can handle different errors by checking response.status.code
            // and also we can use the message sent from the server to pass it throw the exception
            // and show it to user if we want
            if (response.status.code == 404) {
                throw GeneralException.NotFoundException
            } else {
                throw GeneralException.UnknownErrorException
            }
        }
    }
}
