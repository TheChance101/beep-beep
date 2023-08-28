package org.thechance.api_gateway.endpoints.model

import kotlinx.serialization.Serializable


@Serializable
data class Cuisine(
    val id : String,
    val name : String
)
