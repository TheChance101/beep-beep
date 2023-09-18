package data.gateway.remote

import data.remote.mapper.toEntity
import data.remote.model.LocationDto
import data.remote.model.NotificationDto
import data.remote.model.OfferDto
import data.remote.model.RestaurantDto
import domain.entity.Notification
import domain.entity.Offer
import domain.entity.Restaurant
import domain.entity.User
import domain.gateway.IFakeRemoteGateway

class FakeRemoteGateway : IFakeRemoteGateway {

    override suspend fun getFavoriteRestaurants(): List<Restaurant> {
        return restaurants.map { it.toEntity() }
    }

    override suspend fun getUsrWallet(): User {
        return User(name = "Test", currency = "$", walletValue = 100.0)
    }

    override fun getNewOffers(): List<Offer> {
        return offers.map { it.toEntity() }
    }

    override suspend fun getNotificationHistory(): List<Notification> {
        return notifications.map{ it.toEntity() }
    }

    private val restaurants = listOf(
        RestaurantDto(
            id = "64f372095fecc11e6d917656",
            ownerId = "64f3663e5ddbc15bfd1efcfa",
            ownerUsername = "hamada",
            name = "Hamada Market",
            rate = 4.5,
            priceLevel = "$$",
            phone = "1234567890",
            description = "This is description",
            closingTime = "07:30",
            openingTime = "22:00",
            location = LocationDto(22.0, 10.5),
            address = "Main street, 123"
        ),
        RestaurantDto(
            id = "64f373b35fecc11e6d917659",
            ownerId = "64f3663e5ddbc15bfd1efcfa",
            ownerUsername = "masala",
            name = "Masala Restaurant",
            rate = 3.5,
            priceLevel = "$",
            phone = "1234567890",
            description = "This is description",
            closingTime = "09:00",
            openingTime = "20:00",
            location = LocationDto(12.0, 10.5),
            address = "New street, 23"
        ),
        RestaurantDto(
            id = "64f373be5fecc11e6d91765a",
            ownerId = "64f3663e5ddbc15bfd1efcfa",
            ownerUsername = "the_chance",
            name = "Chance Market",
            rate = 5.0,
            priceLevel = "$$$",
            phone = "1234567890",
            description = "This is description",
            closingTime = "08:30",
            openingTime = "17:00",
            location = LocationDto(33.0, 22.5),
            address = "Cairo street, 101"
        )
    )

    private val offers = listOf(
        OfferDto(
            "000-0d-d0-d0--00d0d0d",
            "https://s3-alpha-sig.figma.com/img/74ff/c283/fb93589c0fb95bffae890164ec2aba74?Expires=1694390400&Signature=hKha19ACxyhhz-Wmwn~npU9SX52UpPYLqkqBT6hx1YZ6f~RG6XrFJhEcpsFMaw5kXGYS5g7hrysHkk5PbiCLzSk3OJEQNBQeNJlCyWKT-f5ckGEbkDYfvO5WmipDAMMHSTxilNY3-xK9h3vQyOCbZZdY75b8k7cuPLrAMmKBRRDM5NJeSGSJGrrNBQNhsK8MjlxUE5aHg2Sqy8UKseNPZsks4lWNWVXmI3QDgEooOXC5CzZ6ivorXagM7s-1-WOZYdTkr0F9mPZZ8rfFZzMeF~OvokA6gd4-6IXQaK4gWoQwVTSgGxMspmcqQU59EOTESRoVwd4CLaf-lFXU9vCaaQ__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
        ),
        OfferDto(
            "000-0d-d0-d0--00d0d0dacsccs",
            "https://s3-alpha-sig.figma.com/img/44f7/7fd1/8c37e4958c7caca679d133c2374c85a6?Expires=1694390400&Signature=j9wjjnLOGs1wG-C8T1riMy6npf-G0V0VARSQGtIgqM3L0lCMY-yJbBcWEEsoGuUdPXZzGTlon-RWMDnRChj2k51akbCp4rLuv-UYdtMIDXljS5hcGdVi0bYkI7V4nY4Jp2vkQdKZuY73N5Yy49WfyElVfsB8n1Qgi4hMxtsicAzF0-5bZnyaLQZda2LPK-rWVH2yj5rhR2evzrcrKYRVY2GaYNQQ58zsHdwvieq4IKlHx44RPg~x2kG~ObSAmzW1iQ8LEEF8~jhI9S~Iw3TSbejP0T64qYpA21erIX8DPLfygUOB0XvhT41vsR-nkVsiYRHXcIyAet-Apj5V5xCevA__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
        ),
        OfferDto(
            "000-0casscac--c-s-cs",
            "https://s3-alpha-sig.figma.com/img/74ff/c283/fb93589c0fb95bffae890164ec2aba74?Expires=1694390400&Signature=hKha19ACxyhhz-Wmwn~npU9SX52UpPYLqkqBT6hx1YZ6f~RG6XrFJhEcpsFMaw5kXGYS5g7hrysHkk5PbiCLzSk3OJEQNBQeNJlCyWKT-f5ckGEbkDYfvO5WmipDAMMHSTxilNY3-xK9h3vQyOCbZZdY75b8k7cuPLrAMmKBRRDM5NJeSGSJGrrNBQNhsK8MjlxUE5aHg2Sqy8UKseNPZsks4lWNWVXmI3QDgEooOXC5CzZ6ivorXagM7s-1-WOZYdTkr0F9mPZZ8rfFZzMeF~OvokA6gd4-6IXQaK4gWoQwVTSgGxMspmcqQU59EOTESRoVwd4CLaf-lFXU9vCaaQ__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
        ),
        OfferDto(
            "000-0wdwdwdww-d-w-d-wd-dw",
            "https://s3-alpha-sig.figma.com/img/44f7/7fd1/8c37e4958c7caca679d133c2374c85a6?Expires=1694390400&Signature=j9wjjnLOGs1wG-C8T1riMy6npf-G0V0VARSQGtIgqM3L0lCMY-yJbBcWEEsoGuUdPXZzGTlon-RWMDnRChj2k51akbCp4rLuv-UYdtMIDXljS5hcGdVi0bYkI7V4nY4Jp2vkQdKZuY73N5Yy49WfyElVfsB8n1Qgi4hMxtsicAzF0-5bZnyaLQZda2LPK-rWVH2yj5rhR2evzrcrKYRVY2GaYNQQ58zsHdwvieq4IKlHx44RPg~x2kG~ObSAmzW1iQ8LEEF8~jhI9S~Iw3TSbejP0T64qYpA21erIX8DPLfygUOB0XvhT41vsR-nkVsiYRHXcIyAet-Apj5V5xCevA__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
        )
    )

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
