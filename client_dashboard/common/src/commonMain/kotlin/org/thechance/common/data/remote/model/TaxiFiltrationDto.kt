package org.thechance.common.data.remote.model

import kotlinx.serialization.SerialName

data class TaxiFiltrationDto(
    @SerialName("color")
    val color: Long?,
    @SerialName("seats")
    val seats: Int?,
    @SerialName("status")
    val status: Int?,
)