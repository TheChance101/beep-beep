package org.thechance.api_gateway.data.model

import io.ktor.http.*
import kotlinx.serialization.Serializable
import org.thechance.api_gateway.endpoints.utils.toListOfIntOrNull
import org.thechance.api_gateway.endpoints.utils.toListOfStringOrNull

@Serializable
data class UserOptions(
    val page: Int?,
    val limit: Int?,
    val query: String?,
    val permissions: List<Int>?,
    val country: List<String>?
)


fun getUserOptions(parameters: Parameters): UserOptions {
    val page = parameters["page"]?.toIntOrNull()
    val limit = parameters["limit"]?.toIntOrNull()

    val query = parameters["query"]?.trim()
    val permissions = parameters["permissions"].toListOfIntOrNull()
    val countries = parameters["countries"].toListOfStringOrNull()
    return UserOptions(page, limit, query, permissions, countries)
}

