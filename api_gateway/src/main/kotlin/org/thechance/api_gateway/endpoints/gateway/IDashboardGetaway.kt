package org.thechance.api_gateway.endpoints.gateway

import org.thechance.api_gateway.data.model.Cuisine
import java.util.*

interface IDashboardGetaway {
    suspend fun addCuisine(name: String, id: String, permissions: List<Int>, locale: Locale): Cuisine

    suspend fun getCuisines()
}