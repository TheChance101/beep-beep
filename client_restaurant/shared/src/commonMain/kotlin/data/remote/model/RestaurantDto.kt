package data.remote.model

import domain.entity.Restaurant

data class RestaurantDto(
    val id: String? = null,
    val ownerId: String,
    val name: String? = null,
    val description: String? = null,
    val priceLevel: String? = null,
    val rate: Double? = null,
    val phone: String? = null,
    val openingTime: String? = null,
    val closingTime: String? = null,
    val address: AddressDto? = null
)

fun List<RestaurantDto>.toEntity():List<Restaurant> = map { it.toEntity() }

fun RestaurantDto.toEntity(): Restaurant {
    return Restaurant(
        id = id,
        ownerId = ownerId,
        name = name,
        description = description,
        priceLevel = priceLevel,
        rate = rate,
        phone = phone,
        openingTime = openingTime,
        closingTime = closingTime,
        address = address?.toEntity()
    )
}