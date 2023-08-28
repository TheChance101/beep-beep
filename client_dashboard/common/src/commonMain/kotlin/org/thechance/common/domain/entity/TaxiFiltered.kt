package org.thechance.common.domain.entity

import org.thechance.common.domain.util.TaxiStatus

data class TaxiFiltered(
    val carColor: CarColor,
    val seats: Int,
    val status: TaxiStatus,
)