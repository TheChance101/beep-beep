package org.thechance.service_restaurant.api.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.receiveParameters
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_restaurant.api.models.mappers.toDto
import org.thechance.service_restaurant.domain.usecase.IManageOrderUseCase
import org.thechance.service_restaurant.domain.utils.OrderStatus
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_REQUEST_PARAMETER
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException
import org.thechance.service_restaurant.domain.utils.exceptions.NOT_FOUND

fun Route.orderRoutes(){
    val  manageOrder: IManageOrderUseCase by inject()
    route("restaurant/order"){

        get("/{id}"){
            val id = call.parameters["id"] ?: throw MultiErrorException(listOf(NOT_FOUND))

            val result =manageOrder.getOrderById(id)
            call.respond(HttpStatusCode.OK,result.toDto())
        }

        post("/{id}/status"){
            val id = call.parameters["id"] ?: throw MultiErrorException(listOf(NOT_FOUND))
            val status = call.receiveParameters()["status"]?.toInt() ?: throw MultiErrorException(listOf(INVALID_REQUEST_PARAMETER))

            val result =manageOrder.updateOrderStatus(id, OrderStatus.getOrderStatus(status))
            call.respond(HttpStatusCode.OK,result)
        }

        get("/history/{id}"){
            val id = call.parameters["id"] ?: throw MultiErrorException(listOf(NOT_FOUND))
            val page= call.parameters["page"]?.toInt() ?: 1
            val limit= call.parameters["limit"]?.toInt() ?:10

            val result =manageOrder.getOrdersHistory(id,page,limit)
            call.respond(HttpStatusCode.OK,result.map { it.toDto() })
        }
    }
}