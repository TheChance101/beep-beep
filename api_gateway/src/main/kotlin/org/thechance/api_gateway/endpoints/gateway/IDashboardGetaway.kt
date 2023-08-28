package org.thechance.api_gateway.endpoints.gateway

import org.thechance.api_gateway.data.model.CuisineResource
import java.util.*

interface IDashboardGetaway {
    suspend fun addCuisine(name: String,  permissions: List<Int>, locale: Locale): CuisineResource

    suspend fun getCuisines(locale: Locale):CuisineResource
}