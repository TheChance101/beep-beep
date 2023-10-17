package data.remote.mapper

import data.remote.model.OrderDto
import domain.entity.Order
import domain.entity.Price

fun OrderDto.toEntity() = Order(
    id = id ?: "",
    restaurantId = restaurantId ?: "",
    restaurantName = restaurantName ?: "",
    restaurantImageUrl = if (restaurantImage.isNullOrBlank()) {
        "https://scontent-sea1-1.xx.fbcdn.net/v/t39.30808-6/358108877_873395391020679_730609755314452340_n.jpg?_nc_cat=109&ccb=1-7&_nc_sid=5f2048&_nc_ohc=msqwWHVXii0AX_jja4K&_nc_ht=scontent-sea1-1.xx&oh=00_AfCnfbZBuizkLj5HOvajIfwQWOK8pGbHzof38z2Nk2VVSQ&oe=652D57F0"
    } else {
        restaurantImage
    },
    meals = meals?.toEntity(restaurantName, currency = currency) ?: emptyList(),
    price = Price(totalPrice ?: 0.0, currency = currency ?: ""),
    createdAt = createdAt ?: 0L,
    orderStatus = orderStatus,
)

fun List<OrderDto>.toEntity() = map { it.toEntity() }
