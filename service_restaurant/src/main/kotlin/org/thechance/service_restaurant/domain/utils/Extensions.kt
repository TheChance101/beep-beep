package org.thechance.service_restaurant.domain.utils

fun Boolean.nullIfFalse(): Any? = if (!this) { null } else { Unit }