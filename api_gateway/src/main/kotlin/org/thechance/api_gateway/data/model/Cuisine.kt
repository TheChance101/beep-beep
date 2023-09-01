package org.thechance.api_gateway.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Cuisine(val id: String? = null, val name: String)