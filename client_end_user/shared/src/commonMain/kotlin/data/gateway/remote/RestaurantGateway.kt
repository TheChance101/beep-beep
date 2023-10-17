package data.gateway.remote

import data.remote.mapper.toCuisineEntity
import data.remote.mapper.toEntity
import data.remote.model.CuisineDto
import data.remote.model.MealDto
import data.remote.model.MealRestaurantDto
import data.remote.model.OfferDto
import data.remote.model.RestaurantDto
import data.remote.model.ServerResponse
import domain.entity.Cuisine
import domain.entity.Meal
import domain.entity.Offer
import domain.entity.Restaurant
import domain.gateway.IRestaurantGateway
import domain.utils.GeneralException
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RestaurantGateway(client: HttpClient) : BaseGateway(client = client), IRestaurantGateway {
    override suspend fun getCuisines(): List<Cuisine> {
        return tryToExecute<ServerResponse<List<CuisineDto>>> {
            get("/cuisines")
        }.value?.toCuisineEntity() ?: throw GeneralException.NotFoundException
    }

    override suspend fun getRestaurantDetails(restaurantId: String): Restaurant {
        return tryToExecute<ServerResponse<RestaurantDto>> {
            get("/restaurant/$restaurantId")
        }.value?.toEntity() ?: throw GeneralException.NotFoundException
    }

    override suspend fun getMealById(mealId: String): Meal {
        return tryToExecute<ServerResponse<MealDto>> {
            get("/meal/$mealId")
        }.value?.toEntity() ?: throw GeneralException.NotFoundException
    }

    override suspend fun getNewOffers(): List<Offer> {
        // todo: implement this when the backend is ready
        return offers.map { it.toEntity() }
    }

    override suspend fun getMostOrdersMeal(restaurantId: String): List<Meal> {
        // todo: implement this when the backend is ready
        return emptyList()
    }


    override suspend fun search(query: String): Pair<List<Restaurant>, List<Meal>> {
        val result = tryToExecute<ServerResponse<MealRestaurantDto>> {
            get("/restaurants/search?query=$query")
        }.value ?: throw GeneralException.NotFoundException
        return Pair(result.restaurants.toEntity(), result.meals.toEntity())
    }

    override suspend fun getMealsInCuisine(cuisineId: String): List<Meal> {
        return tryToExecute<ServerResponse<List<MealDto>>> {
            get("/cuisine/$cuisineId/meals")
        }.value?.toEntity() ?: throw GeneralException.NotFoundException
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
}
