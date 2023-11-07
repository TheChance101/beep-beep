package data.remote.mapper

import data.remote.model.RestaurantDto
import domain.entity.PriceLevel
import domain.entity.Restaurant
import domain.entity.Time

fun RestaurantDto.toEntity(): Restaurant {

    val openingTimeParts = openingTime?.split(":")
    val closingTimeParts = closingTime?.split(":")


    return Restaurant(
        id = id,
        ownerId = ownerId ?: "",
        ownerUsername = ownerUsername ?: "",
        name = name ?: "",
        description = description ?: "",
        priceLevel = PriceLevel.getPriceLevel(priceLevel ?: "") ?: PriceLevel.LOW,
        rate = rate ?: 0.0,
        phone = phone ?: "",
        openingTime = Time(
            hours = openingTimeParts?.get(0)?.toInt() ?: 0,
            minutes = openingTimeParts?.get(1)?.toInt() ?: 0
        ),
        closingTime = Time(
            hours = closingTimeParts?.get(0)?.toInt() ?: 0,
            minutes = closingTimeParts?.get(1)?.toInt() ?: 0
        ),
        location = location.toEntity(),
        address = address ?: "",
        image = image
            ?: "https://scontent-sea1-1.xx.fbcdn.net/v/t39.30808-6/358108877_873395391020679_730609755314452340_n.jpg?_nc_cat=109&ccb=1-7&_nc_sid=5f2048&_nc_ohc=msqwWHVXii0AX_jja4K&_nc_ht=scontent-sea1-1.xx&oh=00_AfCnfbZBuizkLj5HOvajIfwQWOK8pGbHzof38z2Nk2VVSQ&oe=652D57F0"
    )
}


fun List<RestaurantDto>.toEntity() = map { it.toEntity() }
