package org.thechance.service_restaurant.api.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.receiveParameters
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.sendSerialized
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import org.koin.ktor.ext.inject
import org.thechance.service_restaurant.api.models.mappers.toDto
import org.thechance.service_restaurant.domain.usecase.IManageOrderUseCase
import org.thechance.service_restaurant.domain.usecase.IManageRestaurantDetailsUseCase
import org.thechance.service_restaurant.domain.utils.OrderStatus
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_REQUEST_PARAMETER
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException
import org.thechance.service_restaurant.domain.utils.exceptions.NOT_FOUND

fun Route.orderRoutes(){
    val  manageOrder: IManageOrderUseCase by inject()
    val  manageRestaurants: IManageRestaurantDetailsUseCase by inject()

    route("restaurant/order"){

        webSocket("/{id}"){
            val id = call.parameters["id"] ?: throw MultiErrorException(listOf(NOT_FOUND))
            val restaurant =  manageRestaurants.getRestaurant(id).toDto()
            val isOpen= manageOrder.checkRestaurantOpen(
                openingTime=restaurant.openingTime?:"",
                closingTime=restaurant.closingTime?:"")
            if (isOpen) {
                try{
                    val orders = manageOrder.getOrdersByRestaurantId(id)
                    sendSerialized( orders.map { it.toDto()})
                }catch (e:Exception){
                    close(CloseReason(CloseReason.Codes.CANNOT_ACCEPT, "Restaurant is closed"))
                }

            }else{
                close(CloseReason(CloseReason.Codes.GOING_AWAY, "Restaurant is closed"))
                return@webSocket
            }
        }

        get("details/{id}"){
            val id = call.parameters["id"] ?: throw MultiErrorException(listOf(NOT_FOUND))

            val result =manageOrder.getOrderById(orderId=id)
            call.respond(HttpStatusCode.OK,result.toDto())
        }

        post("/status/{id}"){
            val id = call.parameters["id"] ?: throw MultiErrorException(listOf(NOT_FOUND))
            val status = call.receiveParameters()["status"]?.toInt() ?: throw MultiErrorException(listOf(INVALID_REQUEST_PARAMETER))

            val result =manageOrder.updateOrderStatus(
                orderId= id,status= OrderStatus.getOrderStatus(status))
            call.respond(HttpStatusCode.OK,result)
        }

        get("/history/{id}"){
            val id = call.parameters["id"] ?: throw MultiErrorException(listOf(NOT_FOUND))
            val page= call.parameters["page"]?.toInt() ?: 1
            val limit= call.parameters["limit"]?.toInt() ?:10

            val result =manageOrder.getOrdersHistory(restaurantId=id,page=page,limit=limit)
            call.respond(HttpStatusCode.OK,result.map { it.toDto() })
        }
    }
}