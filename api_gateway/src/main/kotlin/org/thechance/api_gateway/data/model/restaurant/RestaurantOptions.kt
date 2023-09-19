package org.thechance.api_gateway.data.model.restaurant

import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
data class RestaurantOptions(
    val page: Int?,
    val limit: Int?,
    val query: String?,
    val rating: Double?,
    val priceLevel: String?
)

fun getRestaurantOptions(parameters: Parameters): RestaurantOptions {
    val page = parameters["page"]?.toIntOrNull()
    val limit = parameters["limit"]?.toIntOrNull()

    val query = parameters["query"]?.trim()
    val rating = parameters["rating"]?.trim()?.toDoubleOrNull()
    val priceLevel = parameters["priceLevel"]
    return RestaurantOptions(page, limit, query, rating, priceLevel)
}