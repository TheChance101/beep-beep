package org.thechance.service_identity.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID


@Serializable
data class RefreshTokensCollection(
    @Contextual
    val userId: UUID,
    val refreshToken: String,
    val expirationDate: Long,
)

