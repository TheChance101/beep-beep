package org.thechance.service_identity.endpoints.model

import kotlinx.serialization.Serializable


@Serializable
data class UserOptionsDto(
    val page: Int? = null,
    val limit: Int? = null,
    val query: String? = null,
    val permissions: List<Int>? = null,
    val country: List<String>? = null
)
