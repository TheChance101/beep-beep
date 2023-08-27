package data.remote.model

data class RestaurantDto(
    val id: String,
    val ownerId: String,
    val ownerUsername: String? = null,
    val name: String? = null,
    val description: String? = null,
    val priceLevel: String? = null,
    val rate: Double? = null,
    val phone: String? = null,
    val openingTime: String? = null,
    val closingTime: String? = null,
    val address: AddressDto
)

