package org.thechance.common.data.gateway.fake

import org.thechance.common.data.gateway.remote.mapper.toEntity
import org.thechance.common.data.gateway.remote.model.DataWrapperDto
import org.thechance.common.data.gateway.remote.model.RestaurantDto
import org.thechance.common.data.gateway.remote.model.toEntity
import org.thechance.common.domain.entity.Cuisine
import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.NewRestaurantInfo
import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.getway.IRestaurantGateway
import java.util.UUID
import kotlin.math.ceil

class RestaurantFakeGateway : IRestaurantGateway {

    override suspend fun getRestaurants(
        pageNumber: Int,
        numberOfRestaurantsInPage: Int,
        restaurantName: String,
        rating: Double,
        priceLevel: Int,
    ): DataWrapper<Restaurant> {
        var restaurants = restaurants.toEntity()
        val priceLevelFilter = if (priceLevel != 0) "$".repeat(priceLevel) else null
        if (restaurantName.isNotEmpty()) {
            restaurants = restaurants.filter {
                it.name.startsWith(
                    restaurantName,
                    true
                )
            }
        }

        if (rating > 0.0) {
            restaurants = restaurants.filter {
                it.rate == rating
            }
        }

        if (priceLevel > 0) {
            restaurants = restaurants.filter {
                it.priceLevel == priceLevelFilter
            }
        }

        val startIndex = (pageNumber - 1) * numberOfRestaurantsInPage
        val endIndex = startIndex + numberOfRestaurantsInPage
        val numberOfPages = ceil(restaurants.size / (numberOfRestaurantsInPage * 1.0)).toInt()
        return try {
            DataWrapperDto(
                totalPages = numberOfPages,
                result = restaurants.subList(startIndex, endIndex.coerceAtMost(restaurants.size)),
                totalResult = restaurants.size
            ).toEntity()
        } catch (e: Exception) {
            DataWrapperDto(
                totalPages = numberOfPages,
                result = restaurants,
                totalResult = restaurants.size
            ).toEntity()
        }
    }

    override suspend fun createRestaurant(restaurant: NewRestaurantInfo): Restaurant {
        return Restaurant(
            id = "7",
            name = restaurant.name,
            ownerId = restaurant.ownerUsername,
            phone = restaurant.phoneNumber,
            openingTime = restaurant.openingTime,
            closingTime = restaurant.closingTime,
            rate = 0.0,
            priceLevel = "",
            ownerUsername = restaurant.ownerUsername
        )
    }

    override suspend fun deleteRestaurant(id: String): Boolean {
        restaurants.remove(restaurants.find { it.id == id })
        return true
    }

    override suspend fun getCuisines(): List<Cuisine> {
        return cuisines
    }


    override suspend fun createCuisine(cuisineName: String): Cuisine {
        val newCuisine = Cuisine(UUID.randomUUID().toString(), cuisineName)
        cuisines.add(newCuisine)
        return newCuisine
    }

    override suspend fun deleteCuisine(cuisineId: String) {
        cuisines.find { it.id == cuisineId }?.let {
            cuisines.remove(it)
        }
    }

    private val cuisines = mutableListOf<Cuisine>(
        Cuisine("1", "Angolan cuisine"),
        Cuisine("", "Cameroonian cuisine"),
        Cuisine("", "Chadian cuisine"),
        Cuisine("", "Congolese cuisine"),
        Cuisine("", "Centrafrican cuisine"),
        Cuisine("", "Equatorial Guinea cuisine"),
        Cuisine("", "Gabonese cuisine"),
        Cuisine("", "Santomean cuisine"),
        Cuisine("", "Burundian cuisine"),
        Cuisine("", "Djiboutian cuisine"),
        Cuisine("", "Eritrean cuisine"),
        Cuisine("", "Ethiopian cuisine"),
        Cuisine("", "Kenyan cuisine"),
        Cuisine("", "Maasai cuisine"),
        Cuisine("", "Rwandan cuisine"),
        Cuisine("", "Somali cuisine"),
        Cuisine("", "South Sudanese cuisine"),
        Cuisine("", "Tanzanian cuisine"),
        Cuisine("", "Zanzibari cuisine"),
        Cuisine("", "Ugandan cuisine"),
    )

    private val restaurants = mutableListOf(
        RestaurantDto(
            id = "8c90c4c6-1e69-47f3-aa59-2edcd6f0057b",
            name = "Mujtaba Restaurant",
            ownerId = "mujtaba",
            phone = "0532465722",
            rate = 0.4,
            priceLevel = "",
            openingTime = "06:30 - 22:30"
        ),
        RestaurantDto(
            id = "6e21s4f-aw32-fs3e-fe43-aw56g4yr324",
            name = "Karrar Restaurant",
            ownerId = "karrar",
            phone = "0535232154",
            rate = 3.5,
            priceLevel = "",
            openingTime = "12:00 - 23:00"
        ),
        RestaurantDto(
            id = "7a33sax-aw32-fs3e-12df-42ad6x352zse",
            name = "Saif Restaurant",
            ownerId = "saif",
            phone = "0554627893",
            rate = 4.0,
            priceLevel = "",
            openingTime = "09:00 - 23:00"
        ),
        RestaurantDto(
            id = "7y1z47c-s2df-76de-dwe2-42ad6x352zse",
            name = "Nada Restaurant",
            ownerId = "nada",
            phone = "0524242766",
            rate = 3.4,
            priceLevel = "",
            openingTime = "01:00 - 23:00"
        ),
        RestaurantDto(
            id = "3e1f5d4a-8317-4f13-aa89-2c094652e6a3",
            name = "Asia Restaurant",
            ownerId = "asia",
            phone = "0528242165",
            rate = 2.9,
            priceLevel = "",
            openingTime = "09:30 - 21:30"
        ),
        RestaurantDto(
            id = "7a1bfe39-4b2c-4f76-bde0-82da2eaf9e99",
            name = "Kamel Restaurant",
            ownerId = "kamel",
            phone = "0528242235",
            rate = 3.0,
            priceLevel = "",
            openingTime = "06:30 - 22:30"
        ),
        RestaurantDto(
            id = "7a1bfe39-4b2c-4f76-bde0-82da2eaf9e55",
            name = "Kamel Restaurant",
            ownerId = "kamel",
            phone = "0528242235",
            rate = 4.9,
            priceLevel = "$",
            openingTime = "06:30 - 22:30"
        ),        RestaurantDto(
            id = "7a1bfe39-4b2c-4f76-bde0-82da2eaf9e89",
            name = "Kamel Restaurant",
            ownerId = "kamel",
            phone = "0528242235",
            rate = 5.0,
            priceLevel = "$$",
            openingTime = "06:30 - 22:30"
        ),
    )

}