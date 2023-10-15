package data.gateway.fake

import data.remote.mapper.toEntity
import data.remote.model.NotificationDto
import domain.entity.Notification
import domain.gateway.INotificationGateway

class FakeNotificationGateway : INotificationGateway {

    override suspend fun getNotificationHistory(): List<Notification> {
        return notifications.map { it.toEntity() }
    }

    private val notifications = listOf(
        NotificationDto(
            id = "64f372095fecc11e6d917656",
            title = "Order is Cancelled",
            body = "Sorry! Yummies Restaurant have so much load they cancelled your order.",
            date = 1695047432000,
            topic = "Order",
            userId = "64f3663e5ddbc15bfd1efcfa"
        ),
        NotificationDto(
            id = "64f372095fecc11e6d917656",
            title = "Order is Cancelled",
            body = "Sorry! Yummies Restaurant have so much load they cancelled your order.",
            date = 1695070832000,
            topic = "Order",
            userId = "64f3663e5ddbc15bfd1efcfa"
        ),
        NotificationDto(
            id = "64f372095fecc11e6d917656",
            title = "Order is Ready",
            body = "Your order is on its way!",
            date = 1694996492000,
            topic = "Order",
            userId = "64f3663e5ddbc15bfd1efcfa"
        ),
        NotificationDto(
            id = "64f372095fecc11e6d917656",
            title = "Cashback 50%",
            body = "Get 50% cashback for the next order from Restaurant Yummies.",
            date = 1695032732000,
            topic = "Order",
            userId = "64f3663e5ddbc15bfd1efcfa"
        ),
        NotificationDto(
            id = "64f372095fecc11e6d917656",
            title = "Cashback 50%",
            body = "Get 50% cashback for the next order from Restaurant Yummies.",
            date = 1695032732000,
            topic = "Order",
            userId = "64f3663e5ddbc15bfd1efcfa"
        ),
    )
}