package data.gateway.remote

import data.remote.mapper.toDeliveryRideEntity
import data.remote.mapper.toDto
import data.remote.mapper.toEntity
import data.remote.mapper.toTaxiRideEntity
import data.remote.mapper.toTripEntity
import data.remote.model.CartDto
import data.remote.model.DeliveryRideDto
import data.remote.model.FoodOrderDto
import data.remote.model.LocationDto
import data.remote.model.MealCartDto
import data.remote.model.PaginationResponse
import data.remote.model.ServerResponse
import data.remote.model.TaxiRideDto
import data.remote.model.TripDto
import domain.entity.Cart
import domain.entity.DeliveryRide
import domain.entity.FoodOrder
import domain.entity.Location
import domain.entity.PaginationItems
import domain.entity.TaxiRide
import domain.entity.Trip
import domain.gateway.ITransactionsGateway
import domain.utils.GeneralException
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.util.InternalAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

class TransactionsGateway(client: HttpClient) : BaseGateway(client = client), ITransactionsGateway {
    override suspend fun getTripHistory(page: Int, limit: Int): PaginationItems<Trip> {
        val result = tryToExecute<ServerResponse<PaginationResponse<TripDto>>> {
            get("/trip/history") {
                parameter("page", page)
                parameter("limit", limit)
            }
        }.value
        return paginateData(
            result = result?.items?.map { it.toTripEntity() }
                ?: throw GeneralException.UnknownErrorException,
            page = result.page, total = result.total
        )
    }

    override suspend fun getOrderHistoryGateway(page: Int, limit: Int): PaginationItems<FoodOrder> {
        val result = tryToExecute<ServerResponse<PaginationResponse<FoodOrderDto>>> {
            get("orders/user/history") {
                parameter("page", page)
                parameter("limit", limit)
            }
        }.value
        return paginateData(
            result = result?.items?.map { it.toEntity() }
                ?: throw GeneralException.NotFoundException,
            page = result.page, total = result.total
        )
    }

    override suspend fun getCart(): Cart {
        return tryToExecute<ServerResponse<CartDto>> {
            get("/cart")
        }.value?.toEntity() ?: throw GeneralException.NotFoundException
    }

    @OptIn(InternalAPI::class)
    override suspend fun addMealToCart(
        mealId: String, restaurantId: String, quantity: Int,
    ): Cart {
        val meal = MealCartDto(mealId = mealId, restaurantId = restaurantId, quantity = quantity)
        return tryToExecute<ServerResponse<CartDto>> {
            put("/cart") {
                body = Json.encodeToString(MealCartDto.serializer(), meal)
            }
        }.value?.toEntity() ?: throw GeneralException.NotFoundException
    }

    override suspend fun orderNow(): Boolean {
        return tryToExecute<ServerResponse<FoodOrderDto>> { post("/cart/orderNow") }.value != null
    }

    @OptIn(InternalAPI::class)
    override suspend fun updateCart(cart: Cart) {
        tryToExecute<ServerResponse<CartDto>> {
            put("/cart/replace") {
                body = Json.encodeToString(CartDto.serializer(), cart.toDto())
            }
        }
    }

    override suspend fun getActiveTaxiTrips(): List<Trip> {
        val response = tryToExecute<ServerResponse<List<TripDto>>> {
            get("/user/active/taxi/trips")
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

    override suspend fun getActiveDeliveryTrips(): List<DeliveryRide> {
        val response = tryToExecute<ServerResponse<List<DeliveryRideDto>>> {
            get("/user/active/delivery/trips")
        }
        if (response.isSuccess) {

            return response.value?.map { it.toDeliveryRideEntity() }
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

    override suspend fun getTripByOrderId(orderId: String): Trip {
        val response = tryToExecute<ServerResponse<TripDto>> {
            get("trip/user/$orderId")
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

    override suspend fun getTripByTripId(tripId: String): Trip {
        val response = tryToExecute<ServerResponse<TripDto>> {
            get("trip/$tripId")
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

    override suspend fun getOrderByOrderId(orderId: String): FoodOrder {
        val response = tryToExecute<ServerResponse<FoodOrderDto>> {
            get("order/$orderId")
        }
        if (response.isSuccess) {
            return response.value?.toEntity()
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

    override suspend fun trackTaxiRide(tripId: String): Flow<TaxiRide> {
        return tryToExecuteWebSocket<TaxiRideDto>(
            path = "trip/track/taxi-ride/$tripId"
        ).map { it.toTaxiRideEntity() }
    }

    override suspend fun trackDeliveryRide(tripId: String): Flow<DeliveryRide> {
        return tryToExecuteWebSocket<DeliveryRideDto>(
            path = "trip/track/delivery-ride/$tripId"
        ).map { it.toDeliveryRideEntity() }
    }

    override suspend fun trackFoodOrderInRestaurant(orderId: String): Flow<FoodOrder> {
        return tryToExecuteWebSocket<FoodOrderDto>(
            path = "order/track/$orderId"
        ).map { it.toEntity() }
    }

    override suspend fun trackDriverLocation(tripId: String): Flow<Location> {
        return tryToExecuteWebSocket<LocationDto>(
            path = "location/receive/$tripId"
        ).map { it.toEntity() }
    }
}
