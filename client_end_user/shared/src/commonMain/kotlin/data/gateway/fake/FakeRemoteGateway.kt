package data.gateway.fake

import data.remote.mapper.toEntity
import data.remote.model.CartDto
import data.remote.model.CartMealDto
import data.remote.model.AddressDto
import data.remote.model.LocationDto
import data.remote.model.NotificationDto
import data.remote.model.OfferDto
import data.remote.model.RestaurantDto
import domain.entity.Cart
import data.remote.model.UserDetailsDto
import domain.entity.Notification
import domain.entity.Offer
import domain.entity.Restaurant
import domain.entity.User
import domain.entity.UserDetails
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
        return notifications.map { it.toEntity() }
    }

    override suspend fun getAllCartMeals(): Cart {
        return CartDto(
            meals = meals,
            totalPrice = 255.0,
            currency = "$"
        ).toEntity()
    }

    override suspend fun getUserProfile(): UserDetails {
        return userProfile.toEntity()
    }

    private val userProfile = UserDetailsDto(
        addresses = listOf(
            AddressDto(
                id = "64f372095fecc11e6d917656",
                address = "Main street, 123",
                location = LocationDto(22.0, 10.5)
            )
        ),
        country = "iraq",
        currency = "IQD",
        email = "aya@gmail.com",
        fullName = "aya",
        id = "64f3663e5ddbc15bfd1efcfa",
        permission = 1,
        username = "ay0o5h",
        walletBalance = 0.0,
        phoneNumber = "1234567890"

    )

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
            "https://s3-alpha-sig.figma.com/img/74ff/c283/fb93589c0fb95bffae890164ec2aba74?Expires=1695600000&Signature=cSB9yZulMLy-7VnhobwW3PavUWML0c5jJopRFJuz1jC2fWvHa~32qyGcqlHAMIPJTDIk~GOkzIv3UnVGKWJ4Zf75xvqJEF7jx6XTWeO5oECoRidQzbF7oY73ubLtarmWRqlqiUz1-~PrXkMq1r38ba~XvOd80~08EJ5MjGVcwsGnClS06Kstl0lQa9KqiLkMW02GLYlTKIF89ANlAjMcKkcCsVsnYcqemMRqa96JjCuMM5g~Fhpfd0LkY9akixUM1P9ixDoZ7AYNWfjnWSgHAqq4Cr~ZRAP4vuCxzekVbcG8I3xT8I5JsUXbsLG0EO3UnqNE2feBRRgfx1sOG13qwg__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
        ),
        OfferDto(
            "000-0d-d0-d0--00d0d0dacsccs",
            "https://s3-alpha-sig.figma.com/img/44f7/7fd1/8c37e4958c7caca679d133c2374c85a6?Expires=1695600000&Signature=bQJPGKU2FfZPuIyU2gEXeJV9Ei9XsRe6ytOGcUIz6mbVzv-g1SJ0hCNg1dXHeKaXEvqEAmXHG-KGQTmiGqldgPCfGWw9a0baZWOfSqrcN-2qPxjkBXZiilrDvhn4UyzF5tDsMwArarP~DpNQ0XcZseHKDGBOZFihi-Dbv8DHhS3qPi4uvi5mrGHltMM9KHkZkLLU5NYQOPUUOXo~A2tg1wk1NI7Zd7h2Jh0v3Rn72o~G1e9dpj8Mqxr-4SZYqY8pBvQTdSXHEIx2uqFuBAobbw4Bi2Fzgdf894XMjjebqcm8b9KSh1RNiIN7y4xD0kb1JCcpV~UFc0HwkyNu9PummQ__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
        ),
        OfferDto(
            "000-0casscac--c-s-cs",
            "https://s3-alpha-sig.figma.com/img/74ff/c283/fb93589c0fb95bffae890164ec2aba74?Expires=1695600000&Signature=cSB9yZulMLy-7VnhobwW3PavUWML0c5jJopRFJuz1jC2fWvHa~32qyGcqlHAMIPJTDIk~GOkzIv3UnVGKWJ4Zf75xvqJEF7jx6XTWeO5oECoRidQzbF7oY73ubLtarmWRqlqiUz1-~PrXkMq1r38ba~XvOd80~08EJ5MjGVcwsGnClS06Kstl0lQa9KqiLkMW02GLYlTKIF89ANlAjMcKkcCsVsnYcqemMRqa96JjCuMM5g~Fhpfd0LkY9akixUM1P9ixDoZ7AYNWfjnWSgHAqq4Cr~ZRAP4vuCxzekVbcG8I3xT8I5JsUXbsLG0EO3UnqNE2feBRRgfx1sOG13qwg__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
        ),
        OfferDto(
            "000-0wdwdwdww-d-w-d-wd-dw",
            "https://s3-alpha-sig.figma.com/img/44f7/7fd1/8c37e4958c7caca679d133c2374c85a6?Expires=1695600000&Signature=bQJPGKU2FfZPuIyU2gEXeJV9Ei9XsRe6ytOGcUIz6mbVzv-g1SJ0hCNg1dXHeKaXEvqEAmXHG-KGQTmiGqldgPCfGWw9a0baZWOfSqrcN-2qPxjkBXZiilrDvhn4UyzF5tDsMwArarP~DpNQ0XcZseHKDGBOZFihi-Dbv8DHhS3qPi4uvi5mrGHltMM9KHkZkLLU5NYQOPUUOXo~A2tg1wk1NI7Zd7h2Jh0v3Rn72o~G1e9dpj8Mqxr-4SZYqY8pBvQTdSXHEIx2uqFuBAobbw4Bi2Fzgdf894XMjjebqcm8b9KSh1RNiIN7y4xD0kb1JCcpV~UFc0HwkyNu9PummQ__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
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

    private val meals = listOf(
        CartMealDto(
            id = "64f372095fecc11e6d917656",
            price = 85.0,
            count = 1,
            restaurantName = "Burger king",
            name = "Double cheese burger",
            currency = "$",
            image = "https://s3-alpha-sig.figma.com/img/87f1/a16c/e905155ab7f153570d77f67ecb3bf1f6?Expires=1696204800&Signature=DmxCysc92O0ScX-GO5hhNt2AmsVGk0-U9she4GbHwfhL8qwvl386HN-kur-Wmw-XGE4OptyvEvDLqhMhhaeypR3Bd8lh2SIk6-BxI2z1D1z35QHeHN-ZEVc6Lq1Ai23jtzRAXVfU3nLVLNKOKnupSdhIWQ1JYMaP8TbRokOQFy7cQR70bSEuhROd2urzy5UDq3zJFaqyt6OTleWf9jwJNv5frK0v351I6bdHR0ekSR6flLcCamfvz3skYu7VZ6VE8QLr-dLzX3KIeeYkiZrgEE4YyR054o5TcE~BJALxaLbbHbvKJnOZTp93Lga7Ki5cSFMx3GAYC87Okpz0GHScQA__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
        ),
        CartMealDto(
            id = "64f372095fecc11e6d917656",
            price = 85.0,
            count = 1,
            restaurantName = "Burger king",
            name = "Double cheese burger",
            currency = "$",
            image = "https://s3-alpha-sig.figma.com/img/87f1/a16c/e905155ab7f153570d77f67ecb3bf1f6?Expires=1696204800&Signature=DmxCysc92O0ScX-GO5hhNt2AmsVGk0-U9she4GbHwfhL8qwvl386HN-kur-Wmw-XGE4OptyvEvDLqhMhhaeypR3Bd8lh2SIk6-BxI2z1D1z35QHeHN-ZEVc6Lq1Ai23jtzRAXVfU3nLVLNKOKnupSdhIWQ1JYMaP8TbRokOQFy7cQR70bSEuhROd2urzy5UDq3zJFaqyt6OTleWf9jwJNv5frK0v351I6bdHR0ekSR6flLcCamfvz3skYu7VZ6VE8QLr-dLzX3KIeeYkiZrgEE4YyR054o5TcE~BJALxaLbbHbvKJnOZTp93Lga7Ki5cSFMx3GAYC87Okpz0GHScQA__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
        ),
        CartMealDto(
            id = "64f372095fecc11e6d917656",
            price = 85.0,
            count = 1,
            restaurantName = "Burger king",
            name = "Double cheese burger",
            currency = "$",
            image = "https://s3-alpha-sig.figma.com/img/87f1/a16c/e905155ab7f153570d77f67ecb3bf1f6?Expires=1696204800&Signature=DmxCysc92O0ScX-GO5hhNt2AmsVGk0-U9she4GbHwfhL8qwvl386HN-kur-Wmw-XGE4OptyvEvDLqhMhhaeypR3Bd8lh2SIk6-BxI2z1D1z35QHeHN-ZEVc6Lq1Ai23jtzRAXVfU3nLVLNKOKnupSdhIWQ1JYMaP8TbRokOQFy7cQR70bSEuhROd2urzy5UDq3zJFaqyt6OTleWf9jwJNv5frK0v351I6bdHR0ekSR6flLcCamfvz3skYu7VZ6VE8QLr-dLzX3KIeeYkiZrgEE4YyR054o5TcE~BJALxaLbbHbvKJnOZTp93Lga7Ki5cSFMx3GAYC87Okpz0GHScQA__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
        ),CartMealDto(
            id = "64f372095fecc11e6d917656",
            price = 85.0,
            count = 1,
            restaurantName = "Burger king",
            name = "Double cheese burger",
            currency = "$",
            image = "https://s3-alpha-sig.figma.com/img/87f1/a16c/e905155ab7f153570d77f67ecb3bf1f6?Expires=1696204800&Signature=DmxCysc92O0ScX-GO5hhNt2AmsVGk0-U9she4GbHwfhL8qwvl386HN-kur-Wmw-XGE4OptyvEvDLqhMhhaeypR3Bd8lh2SIk6-BxI2z1D1z35QHeHN-ZEVc6Lq1Ai23jtzRAXVfU3nLVLNKOKnupSdhIWQ1JYMaP8TbRokOQFy7cQR70bSEuhROd2urzy5UDq3zJFaqyt6OTleWf9jwJNv5frK0v351I6bdHR0ekSR6flLcCamfvz3skYu7VZ6VE8QLr-dLzX3KIeeYkiZrgEE4YyR054o5TcE~BJALxaLbbHbvKJnOZTp93Lga7Ki5cSFMx3GAYC87Okpz0GHScQA__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
        ),CartMealDto(
            id = "64f372095fecc11e6d917656",
            price = 85.0,
            count = 1,
            restaurantName = "Burger king",
            name = "Double cheese burger",
            currency = "$",
            image = "https://s3-alpha-sig.figma.com/img/87f1/a16c/e905155ab7f153570d77f67ecb3bf1f6?Expires=1696204800&Signature=DmxCysc92O0ScX-GO5hhNt2AmsVGk0-U9she4GbHwfhL8qwvl386HN-kur-Wmw-XGE4OptyvEvDLqhMhhaeypR3Bd8lh2SIk6-BxI2z1D1z35QHeHN-ZEVc6Lq1Ai23jtzRAXVfU3nLVLNKOKnupSdhIWQ1JYMaP8TbRokOQFy7cQR70bSEuhROd2urzy5UDq3zJFaqyt6OTleWf9jwJNv5frK0v351I6bdHR0ekSR6flLcCamfvz3skYu7VZ6VE8QLr-dLzX3KIeeYkiZrgEE4YyR054o5TcE~BJALxaLbbHbvKJnOZTp93Lga7Ki5cSFMx3GAYC87Okpz0GHScQA__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
        ),CartMealDto(
            id = "64f372095fecc11e6d917656",
            price = 85.0,
            count = 1,
            restaurantName = "Burger king",
            name = "Double cheese burger",
            currency = "$",
            image = "https://s3-alpha-sig.figma.com/img/87f1/a16c/e905155ab7f153570d77f67ecb3bf1f6?Expires=1696204800&Signature=DmxCysc92O0ScX-GO5hhNt2AmsVGk0-U9she4GbHwfhL8qwvl386HN-kur-Wmw-XGE4OptyvEvDLqhMhhaeypR3Bd8lh2SIk6-BxI2z1D1z35QHeHN-ZEVc6Lq1Ai23jtzRAXVfU3nLVLNKOKnupSdhIWQ1JYMaP8TbRokOQFy7cQR70bSEuhROd2urzy5UDq3zJFaqyt6OTleWf9jwJNv5frK0v351I6bdHR0ekSR6flLcCamfvz3skYu7VZ6VE8QLr-dLzX3KIeeYkiZrgEE4YyR054o5TcE~BJALxaLbbHbvKJnOZTp93Lga7Ki5cSFMx3GAYC87Okpz0GHScQA__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
        ),CartMealDto(
            id = "64f372095fecc11e6d917656",
            price = 85.0,
            count = 1,
            restaurantName = "Burger king",
            name = "Double cheese burger",
            currency = "$",
            image = "https://s3-alpha-sig.figma.com/img/87f1/a16c/e905155ab7f153570d77f67ecb3bf1f6?Expires=1696204800&Signature=DmxCysc92O0ScX-GO5hhNt2AmsVGk0-U9she4GbHwfhL8qwvl386HN-kur-Wmw-XGE4OptyvEvDLqhMhhaeypR3Bd8lh2SIk6-BxI2z1D1z35QHeHN-ZEVc6Lq1Ai23jtzRAXVfU3nLVLNKOKnupSdhIWQ1JYMaP8TbRokOQFy7cQR70bSEuhROd2urzy5UDq3zJFaqyt6OTleWf9jwJNv5frK0v351I6bdHR0ekSR6flLcCamfvz3skYu7VZ6VE8QLr-dLzX3KIeeYkiZrgEE4YyR054o5TcE~BJALxaLbbHbvKJnOZTp93Lga7Ki5cSFMx3GAYC87Okpz0GHScQA__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
        ),CartMealDto(
            id = "64f372095fecc11e6d917656",
            price = 85.0,
            count = 1,
            restaurantName = "Burger king",
            name = "Double cheese burger",
            currency = "$",
            image = "https://s3-alpha-sig.figma.com/img/87f1/a16c/e905155ab7f153570d77f67ecb3bf1f6?Expires=1696204800&Signature=DmxCysc92O0ScX-GO5hhNt2AmsVGk0-U9she4GbHwfhL8qwvl386HN-kur-Wmw-XGE4OptyvEvDLqhMhhaeypR3Bd8lh2SIk6-BxI2z1D1z35QHeHN-ZEVc6Lq1Ai23jtzRAXVfU3nLVLNKOKnupSdhIWQ1JYMaP8TbRokOQFy7cQR70bSEuhROd2urzy5UDq3zJFaqyt6OTleWf9jwJNv5frK0v351I6bdHR0ekSR6flLcCamfvz3skYu7VZ6VE8QLr-dLzX3KIeeYkiZrgEE4YyR054o5TcE~BJALxaLbbHbvKJnOZTp93Lga7Ki5cSFMx3GAYC87Okpz0GHScQA__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
        ),CartMealDto(
            id = "64f372095fecc11e6d917656",
            price = 85.0,
            count = 1,
            restaurantName = "Burger king",
            name = "Double cheese burger",
            currency = "$",
            image = "https://s3-alpha-sig.figma.com/img/87f1/a16c/e905155ab7f153570d77f67ecb3bf1f6?Expires=1696204800&Signature=DmxCysc92O0ScX-GO5hhNt2AmsVGk0-U9she4GbHwfhL8qwvl386HN-kur-Wmw-XGE4OptyvEvDLqhMhhaeypR3Bd8lh2SIk6-BxI2z1D1z35QHeHN-ZEVc6Lq1Ai23jtzRAXVfU3nLVLNKOKnupSdhIWQ1JYMaP8TbRokOQFy7cQR70bSEuhROd2urzy5UDq3zJFaqyt6OTleWf9jwJNv5frK0v351I6bdHR0ekSR6flLcCamfvz3skYu7VZ6VE8QLr-dLzX3KIeeYkiZrgEE4YyR054o5TcE~BJALxaLbbHbvKJnOZTp93Lga7Ki5cSFMx3GAYC87Okpz0GHScQA__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
        ),CartMealDto(
            id = "64f372095fecc11e6d917656",
            price = 85.0,
            count = 1,
            restaurantName = "Burger king",
            name = "Double cheese burger",
            currency = "$",
            image = "https://s3-alpha-sig.figma.com/img/87f1/a16c/e905155ab7f153570d77f67ecb3bf1f6?Expires=1696204800&Signature=DmxCysc92O0ScX-GO5hhNt2AmsVGk0-U9she4GbHwfhL8qwvl386HN-kur-Wmw-XGE4OptyvEvDLqhMhhaeypR3Bd8lh2SIk6-BxI2z1D1z35QHeHN-ZEVc6Lq1Ai23jtzRAXVfU3nLVLNKOKnupSdhIWQ1JYMaP8TbRokOQFy7cQR70bSEuhROd2urzy5UDq3zJFaqyt6OTleWf9jwJNv5frK0v351I6bdHR0ekSR6flLcCamfvz3skYu7VZ6VE8QLr-dLzX3KIeeYkiZrgEE4YyR054o5TcE~BJALxaLbbHbvKJnOZTp93Lga7Ki5cSFMx3GAYC87Okpz0GHScQA__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
        ),
    )
}
