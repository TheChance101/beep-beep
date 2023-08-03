package org.thechance.service_restaurant.data.collection

import kotlinx.serialization.Serializable

@Serializable
data class RestaurantAddressesCollection(
    val addresses: List<AddressCollection> = emptyList()
)

