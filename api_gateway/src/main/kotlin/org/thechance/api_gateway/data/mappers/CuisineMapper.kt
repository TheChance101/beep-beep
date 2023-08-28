package org.thechance.api_gateway.data.mappers

import org.thechance.api_gateway.data.model.CuisineResource
import org.thechance.api_gateway.endpoints.model.Cuisine

fun CuisineResource.toCuisine(): Cuisine {
    return Cuisine(
        id = this.id?:"",
        name = this.name
    )
}