package org.thechance.common.domain.entity

import java.util.Date

data class AddRestaurant(
    val name: String,
    val ownerUsername: String,
    val phoneNumber: String,
    val workingHours: Pair<Date, Date>,
    val location: String,
)


