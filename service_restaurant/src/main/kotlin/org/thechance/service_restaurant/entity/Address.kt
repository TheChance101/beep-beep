package org.thechance.service_restaurant.entity

data class Address(
    val id: String,
    val lat: Long? = null,
    val lon: Long? = null,
    val isDeleted: Boolean = false
)
