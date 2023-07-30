package org.thechance.service_taxi.api.endpoints

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject
import org.thechance.service_taxi.api.models.toDto
import org.thechance.service_taxi.domain.entity.Taxi
import org.thechance.service_taxi.domain.usecase.AddTaxiUseCase
import org.thechance.service_taxi.domain.usecase.GetAllTaxesUseCase

fun Route.taxiRoutes() {
    val getAllTaxesUseCase: GetAllTaxesUseCase by inject()
    val addTaxiUseCase: AddTaxiUseCase by inject()

    route("/taxi") {
        get {
            val result = getAllTaxesUseCase()
            call.respond(HttpStatusCode.OK, result.toDto())
        }

        post {
            val name = call.parameters["name"]?.trim()
            val result = addTaxiUseCase(Taxi(name = name))
            call.respond(HttpStatusCode.OK, result)
        }
    }
}