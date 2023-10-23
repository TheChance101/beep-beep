package org.thechance.api_gateway.endpoints.utils

import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.model.taxi.TripDto
import org.thechance.api_gateway.data.model.taxi.toDeliveryTripResponse
import org.thechance.api_gateway.data.model.taxi.toRideTrackingResponse
import org.thechance.api_gateway.data.model.taxi.toTaxiTripResponse
import org.thechance.api_gateway.data.service.IdentityService
import org.thechance.api_gateway.data.service.RestaurantService
import org.thechance.api_gateway.data.service.TaxiService
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
        } catch (e: Exception) {
            session.close(CloseReason(CloseReason.Codes.NORMAL, e.message.toString()))
        }
    }

    suspend fun tryToCollectAndMapToTaxiTrip(
        values: Flow<TripDto>,
        language: String,
        session: DefaultWebSocketServerSession
    ) {
        try {
            values
                .map { tripDto ->
                    val userInfo = identityService.getUserById(languageCode = language, id = tripDto.clientId ?: "")
                    tripDto.toTaxiTripResponse(userInfo)
                }.collectLatest { value ->
                    session.sendSerialized(value)
                }
        } catch (e: Exception) {
            session.close(CloseReason(CloseReason.Codes.NORMAL, e.message.toString()))
        }
    }

    suspend fun tryToCollectAndMapToDeliveryTrip(
        values: Flow<TripDto>,
        language: String,
        session: DefaultWebSocketServerSession
    ) {
        try {
            values
                .map { tripDto ->
                    val restaurantInfo =
                        restaurantService.getRestaurantInfo(
                            languageCode = language,
                            restaurantId = tripDto.restaurantId ?: ""
                        )
                    tripDto.toDeliveryTripResponse(restaurantInfo)
                }.collectLatest { value ->
                    session.sendSerialized(value)
                }
        } catch (e: Exception) {
            session.close(CloseReason(CloseReason.Codes.NORMAL, e.message.toString()))
        }
    }

    suspend fun tryToTrackTaxiRide(
        values: Flow<TripDto>,
        language: String,
        session: DefaultWebSocketServerSession
    ) {
        try {
            values
                .map { tripDto ->
                    val taxi = taxiService.getTaxiById(tripDto.taxiId ?: "", language)
                    tripDto.toRideTrackingResponse(taxi)
                }.collectLatest { value ->
                    session.sendSerialized(value)
                }
        } catch (e: Exception) {
            session.close(CloseReason(CloseReason.Codes.NORMAL, e.message.toString()))
        }
    }
}