package org.thechance.service_identity.utils

import com.mongodb.client.result.UpdateResult
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.plugins.NotFoundException
import io.ktor.server.response.respond
import org.thechance.service_identity.api.ServerResponse

fun UpdateResult.isDocumentModified(): Boolean {
    return this.modifiedCount > 0 && this.wasAcknowledged()
}
suspend fun ApplicationCall.respondException(e: Exception) {
    when(e) {
        is NotFoundException -> {
            val errorMessage = e.message ?: "Not Found"
            val errorResponse = ServerResponse.error(
                errorMessage=errorMessage,
                code= HttpStatusCode.NotFound.value)
            respond(HttpStatusCode.NotFound,errorResponse)
        }
        else -> {
            val errorMessage = e.message ?: "Error Occurred"
            val errorResponse = ServerResponse.error(
                errorMessage=errorMessage,
                code= HttpStatusCode.InternalServerError.value)
            respond(HttpStatusCode.BadRequest,errorResponse)
        }
    }
}