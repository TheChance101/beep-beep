package org.thechance.api_gateway.data.model

import kotlinx.serialization.Serializable

@Serializable
data class MultipartDto<T>(
    val data: T,
    val image: ByteArray? = null
)