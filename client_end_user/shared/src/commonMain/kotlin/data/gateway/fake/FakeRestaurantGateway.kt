package data.gateway.fake

import data.remote.mapper.toEntity
import data.remote.model.MealDto
import data.remote.model.OfferDto
import domain.entity.Cuisine
import domain.entity.Explore
import domain.entity.InProgressWrapper
import domain.entity.Location
import domain.entity.Meal
import domain.entity.Offer
import domain.entity.Order
import domain.entity.Restaurant
import domain.entity.Taxi
import domain.entity.Trip
import domain.gateway.IRestaurantGateway
import kotlinx.datetime.Clock

class FakeRestaurantGateway : IRestaurantGateway {
    override suspend fun getCuisines(): List<Cuisine> {
        TODO("Not yet implemented")
    }

    override suspend fun getInProgress(): InProgressWrapper {
        return InProgressWrapper(
            taxisOnTheWay = getTaxiOnTheWay(),
            tripsOnTheWay = getActiveRide(),
            ordersOnTheWay = getActiveOrder(),
        )
    }

    override suspend fun getRestaurantDetails(restaurantId: String): Restaurant {
        TODO("Not yet implemented")
    }

    override suspend fun getMealById(mealId: String): Meal {
        return meals.first().toEntity()
    }

    override suspend fun getNewOffers(): List<Offer> {
        return offers.map { it.toEntity() }
    }

    override suspend fun getMostOrdersMeal(restaurantId: String): List<Meal> {
        return meals.map { it.toEntity() }
    }

    override suspend fun getSweets(restaurantId: String): List<Meal> {
        return meals.map { it.toEntity() }
    }

    override suspend fun search(query: String): Explore {
        TODO("Not yet implemented")
    }

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

    private fun getActiveRide(): List<Trip> {
        return listOf(
            Trip(
                id = "khhfhdfhd",
                taxiId = "taxi123",
                taxiPlateNumber = "ABC123",
                driverId = "driver456",
                driverName = "Ali Yasein",
                clientId = "client789",
                startPoint = Location(37.7749, -122.4194),
                destination = Location(37.7831, -122.4039),
                rate = 4.5,
                price = 25.0,
                startDate = "2023-09-20 09:00:00",
                endDate = "2023-09-20 09:30:00",
                timeToArriveInMints = 30
            )
        )
    }

    private fun getTaxiOnTheWay(): List<Taxi> {
        return listOf(
            Taxi(
                id = "khhfhdfhd",
                color = "White",
                plate = "1234BC",
                timeToArriveInMints = 30,
            )
        )
    }

    private fun getActiveOrder(): List<Order> {
        return listOf(
            Order(
                id = "khhfhdfhd",
                userId = "user123",
                restaurantId = "restaurant456",
                restaurantName = "Hamada Market",
                restaurantImageUrl = "",
                meals = listOf(
                    Order.Meal(
                        mealId = "meal789",
                        mealName = "Cheeseburger",
                        quantity = 2
                    ),
                    Order.Meal(
                        mealId = "meal101",
                        mealName = "Fries",
                        quantity = 1
                    )
                ),
                totalPrice = 15.99,
                createdAt = Clock.System.now().epochSeconds,
                orderStatus = 1,
                timeToArriveInMints = 20
            )
        )
    }

    private val meals = listOf(
        MealDto(
            id = "000-0d-d0-d0--00d0d0d",
            name = "Burger",
            price = 10.0,
            currency = "$",
            description = "nice meal",
            image = "https://s3-alpha-sig.figma.com/img/74ff/c283/fb93589c0fb95bffae890164ec2aba74?Expires=1695600000&Signature=cSB9yZulMLy-7VnhobwW3PavUWML0c5jJopRFJuz1jC2fWvHa~32qyGcqlHAMIPJTDIk~GOkzIv3UnVGKWJ4Zf75xvqJEF7jx6XTWeO5oECoRidQzbF7oY73ubLtarmWRqlqiUz1-~PrXkMq1r38ba~XvOd80~08EJ5MjGVcwsGnClS06Kstl0lQa9KqiLkMW02GLYlTKIF89ANlAjMcKkcCsVsnYcqemMRqa96JjCuMM5g~Fhpfd0LkY9akixUM1P9ixDoZ7AYNWfjnWSgHAqq4Cr~ZRAP4vuCxzekVbcG8I3xT8I5JsUXbsLG0EO3UnqNE2feBRRgfx1sOG13qwg__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
        ),
        MealDto(
            id = "000-0d-d0-d0--00d0d0dacsccs",
            name = "Burger",
            price = 10.0,
            description = "nice meal",
            currency = "$",
            image = "https://s3-alpha-sig.figma.com/img/44f7/7fd1/8c37e4958c7caca679d133c2374c85a6?Expires=1695600000&Signature=bQJPGKU2FfZPuIyU2gEXeJV9Ei9XsRe6ytOGcUIz6mbVzv-g1SJ0hCNg1dXHeKaXEvqEAmXHG-KGQTmiGqldgPCfGWw9a0baZWOfSqrcN-2qPxjkBXZiilrDvhn4UyzF5tDsMwArarP~DpNQ0XcZseHKDGBOZFihi-Dbv8DHhS3qPi4uvi5mrGHltMM9KHkZkLLU5NYQOPUUOXo~A2tg1wk1NI7Zd7h2Jh0v3Rn72o~G1e9dpj8Mqxr-4SZYqY8pBvQTdSXHEIx2uqFuBAobbw4Bi2Fzgdf894XMjjebqcm8b9KSh1RNiIN7y4xD0kb1JCcpV~UFc0HwkyNu9PummQ__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
        ),
        MealDto(
            id = "000-0casscac--c-s-cs",
            name = "Burger",
            price = 10.0,
            description = "nice meal",
            currency = "$",
            image = "https://s3-alpha-sig.figma.com/img/74ff/c283/fb93589c0fb95bffae890164ec2aba74?Expires=1695600000&Signature=cSB9yZulMLy-7VnhobwW3PavUWML0c5jJopRFJuz1jC2fWvHa~32qyGcqlHAMIPJTDIk~GOkzIv3UnVGKWJ4Zf75xvqJEF7jx6XTWeO5oECoRidQzbF7oY73ubLtarmWRqlqiUz1-~PrXkMq1r38ba~XvOd80~08EJ5MjGVcwsGnClS06Kstl0lQa9KqiLkMW02GLYlTKIF89ANlAjMcKkcCsVsnYcqemMRqa96JjCuMM5g~Fhpfd0LkY9akixUM1P9ixDoZ7AYNWfjnWSgHAqq4Cr~ZRAP4vuCxzekVbcG8I3xT8I5JsUXbsLG0EO3UnqNE2feBRRgfx1sOG13qwg__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
        ),
        MealDto(
            id = "000-0wdwdwdww-d-w-d-wd-dw",
            name = "Burger",
            price = 10.0,
            description = "nice meal",
            currency = "$",
            image = "https://s3-alpha-sig.figma.com/img/44f7/7fd1/8c37e4958c7caca679d133c2374c85a6?Expires=1695600000&Signature=bQJPGKU2FfZPuIyU2gEXeJV9Ei9XsRe6ytOGcUIz6mbVzv-g1SJ0hCNg1dXHeKaXEvqEAmXHG-KGQTmiGqldgPCfGWw9a0baZWOfSqrcN-2qPxjkBXZiilrDvhn4UyzF5tDsMwArarP~DpNQ0XcZseHKDGBOZFihi-Dbv8DHhS3qPi4uvi5mrGHltMM9KHkZkLLU5NYQOPUUOXo~A2tg1wk1NI7Zd7h2Jh0v3Rn72o~G1e9dpj8Mqxr-4SZYqY8pBvQTdSXHEIx2uqFuBAobbw4Bi2Fzgdf894XMjjebqcm8b9KSh1RNiIN7y4xD0kb1JCcpV~UFc0HwkyNu9PummQ__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
        )
    )
}
