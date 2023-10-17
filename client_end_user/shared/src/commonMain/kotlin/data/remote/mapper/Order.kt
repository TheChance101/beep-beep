package data.remote.mapper

import data.remote.model.OrderDto
import domain.entity.FoodOrder
import kotlinx.datetime.LocalTime

fun OrderDto.toEntity() = FoodOrder(
    id = id ?: "",
    userId = userId ?: "",
    restaurantId = restaurantId ?: "",
    restaurantName = restaurantName ?: "",
    restaurantImageUrl = if (restaurantImage.isNullOrBlank()) {
        "https://scontent-sea1-1.xx.fbcdn.net/v/t39.30808-6/358108877_873395391020679_730609755314452340_n.jpg?_nc_cat=109&ccb=1-7&_nc_sid=5f2048&_nc_ohc=msqwWHVXii0AX_jja4K&_nc_ht=scontent-sea1-1.xx&oh=00_AfCnfbZBuizkLj5HOvajIfwQWOK8pGbHzof38z2Nk2VVSQ&oe=652D57F0"
    } else {
        restaurantImage
    },
    currency = currency ?: "USD",
    meals = meals?.toEntity(restaurantName, currency = currency) ?: emptyList(),
    totalPrice = totalPrice ?: 0.0,
    createdAt = createdAt ?: 0L,
    orderStatus = FoodOrder.OrderStatusInRestaurant.getOrderStatus(orderStatus),
    orderEstimatedTime = LocalTime(0, 30).minute,
)

fun List<OrderDto>.toEntity() = map { it.toEntity() }
