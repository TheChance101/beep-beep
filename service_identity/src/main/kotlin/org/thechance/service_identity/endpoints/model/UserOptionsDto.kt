package org.thechance.service_identity.endpoints.model

import kotlinx.serialization.Serializable


@Serializable
data class UserOptionsDto(
    val page: Int?,
    val limit: Int?,
    val query: String?,
    val permissions: List<Int>?,
    val country: List<String>?
)
