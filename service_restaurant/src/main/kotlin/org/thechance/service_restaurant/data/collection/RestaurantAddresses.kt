package org.thechance.service_restaurant.data.collection

import kotlinx.serialization.Serializable

@Serializable
data class RestaurantAddresses(
    val addresses: List<AddressCollection> = emptyList()
)

