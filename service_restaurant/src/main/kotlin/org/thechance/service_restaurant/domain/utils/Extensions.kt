package org.thechance.service_restaurant.domain.utils

fun Boolean.nullIfTrue(): Any? = if (this) { null } else { Unit }