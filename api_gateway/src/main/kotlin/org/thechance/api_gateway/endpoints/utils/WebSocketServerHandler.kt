package org.thechance.api_gateway.endpoints.utils

import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.model.ServerResponse
import org.thechance.api_gateway.data.model.taxi.*
import org.thechance.api_gateway.data.service.IdentityService
import org.thechance.api_gateway.data.service.RestaurantService
import org.thechance.api_gateway.data.service.TaxiService
import org.thechance.api_gateway.data.utils.LocalizedMessageException
import java.util.concurrent.ConcurrentHashMap

@Single
class WebSocketServerHandler(
    private val identityService: IdentityService,
    private val restaurantService: RestaurantService,
    private val taxiService: TaxiService
) {

    val sessions: ConcurrentHashMap<String, DefaultWebSocketServerSession> = ConcurrentHashMap()

    suspend inline fun <reified T> tryToCollect(values: Flow<T>, session: DefaultWebSocketServerSession) {
        try {
            values.flowOn(Dispatchers.IO).collect { value -> session.sendSerialized(value) }
        } catch (e: LocalizedMessageException) {
            session.send(e.localizedMessage)
            session.close()
        }
    }

    suspend inline fun <reified T> tryToCollectOrders(
        values: Flow<T>,
        session: DefaultWebSocketServerSession,
        successMessage: String,
    ) {
        try {
            values.flowOn(Dispatchers.IO).collectLatest { value ->
                session.sendSerialized(ServerResponse.success(value, successMessage))
            }
        } catch (e: LocalizedMessageException) {
            val errorResponse = ServerResponse.error(e.errorMessages, 404)
            session.sendSerialized(errorResponse)
            session.close()
        }
    }

    suspend fun tryToCollectAndMapToTaxiTrip(
        values: Flow<TripDto>,
        successMessage: String,
        language: String,
        session: DefaultWebSocketServerSession
    ) {
        try {
            values
                .map { tripDto ->
                    val userInfo = identityService.getUserById(languageCode = language, id = tripDto.clientId ?: "")
                    tripDto.toTaxiTripResponse(userInfo)
                }.collectLatest { value ->
                    session.sendSerialized(ServerResponse.success(value, successMessage))
                }
        } catch (e: LocalizedMessageException) {
            val errorResponse = ServerResponse.error(e.errorMessages, 404)
            session.sendSerialized(errorResponse)
            session.close()
        }
    }

    suspend fun tryToCollectAndMapToDeliveryTrip(
        values: Flow<TripDto>,
        successMessage: String,
        language: String,
        session: DefaultWebSocketServerSession
    ) {
        try {
            values
                .map { tripDto ->
                    val restaurantInfo =
                        restaurantService.getRestaurantInfo(
                            languageCode = language,
                            restaurantId = tripDto.clientId ?: ""
                        )
                    tripDto.toDeliveryTripResponse(restaurantInfo)
                }.collectLatest { value ->
                    session.sendSerialized(ServerResponse.success(value, successMessage))
                }
        } catch (e: LocalizedMessageException) {
            val errorResponse = ServerResponse.error(e.errorMessages, 404)
            session.sendSerialized(errorResponse)
            session.close()
        }
    }

    suspend fun tryToTrackTaxiRide(
        values: Flow<TripDto>,
        successMessage: String,
        language: String,
        session: DefaultWebSocketServerSession
    ) {

        try {
            values
                .map { tripDto ->
                    val taxi = taxiService.getTaxiById(tripDto.taxiId ?: "", language)
                    tripDto.toRideTrackingResponse(taxi)
                }.collectLatest { value ->
                    session.sendSerialized(ServerResponse.success(value, successMessage))
                }
        } catch (e: LocalizedMessageException) {
            val errorResponse = ServerResponse.error(e.errorMessages, 404)
            session.sendSerialized(errorResponse)
            session.close()
        }
    }


    // we can use the first fun above later and make it reusable
    suspend fun tryToTrackOrder(
        values: Flow<TripDto>,
        successMessage: String,
        session: DefaultWebSocketServerSession
    ) {
        try {
            values
                .map { tripDto ->
                    tripDto.toDeliveryTrackingResponse()
                }.collectLatest { value ->
                    session.sendSerialized(ServerResponse.success(value, successMessage))
                }
        } catch (e: LocalizedMessageException) {
            val errorResponse = ServerResponse.error(e.errorMessages, 404)
            session.sendSerialized(errorResponse)
            session.close()
        }
    }
}