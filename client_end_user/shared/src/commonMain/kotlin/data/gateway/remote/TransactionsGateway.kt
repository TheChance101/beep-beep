package data.gateway.remote

import data.remote.mapper.toDeliveryRideEntity
import data.remote.mapper.toEntity
import data.remote.mapper.toTaxiRideEntity
import data.remote.mapper.toTripEntity
import data.remote.model.CartDto
import data.remote.model.DeliveryRideDto
import data.remote.model.FoodOrderDto
import data.remote.model.PaginationResponse
import data.remote.model.ServerResponse
import data.remote.model.TaxiRideDto
import data.remote.model.TripDto
import domain.entity.Cart
import domain.entity.DeliveryRide
import domain.entity.FoodOrder
import domain.entity.TaxiRide
import domain.entity.Trip
import domain.gateway.ITransactionsGateway
import domain.utils.GeneralException
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.receiveDeserialized
import io.ktor.client.plugins.websocket.wss
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class TransactionsGateway(client: HttpClient) : BaseGateway(client = client), ITransactionsGateway {
    override suspend fun getTripHistory(): List<Trip> {
        //TODO("Not yet implemented")
        return emptyList()
    }

    override suspend fun getOrderHistoryGateway(): List<FoodOrder> {
        return tryToExecute<ServerResponse<PaginationResponse<FoodOrderDto>>> {
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
            get("/user/active/trips")
        }
        if (response.isSuccess) {

            return response.value?.toTripEntity()
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
        val response = tryToExecute<ServerResponse<List<FoodOrderDto>>> {
            get("/active/orders")
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

    override suspend fun trackTaxiRide(tripId: String): Flow<TaxiRide> {
        return tryToExecuteWebSocket<TaxiRideDto>(
            path = "ws://192.168.1.10:8081/trip/track/ride/$tripId"
        ).map { it.toTaxiRideEntity() }
    }

    override suspend fun trackDeliveryRide(tripId: String): Flow<DeliveryRide> {
        return tryToExecuteWebSocket<DeliveryRideDto>(
            path = "/trip/track/delivery/$tripId"
        ).map { it.toDeliveryRideEntity() }
    }

    override suspend fun trackFoodOrderInRestaurant(orderId: String): Flow<FoodOrder> {
        return tryToExecuteWebSocket<FoodOrderDto>(
            path = "order/track/$orderId"
        ).map { it.toEntity() }
    }

}
