package org.thechance.common.data.remote.fakegateway

import org.thechance.common.data.remote.mapper.toEntity
import org.thechance.common.data.remote.model.DataWrapperDto
import org.thechance.common.data.remote.model.RestaurantDto
import org.thechance.common.data.remote.model.toEntity
import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.NewRestaurantInfo
import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.getway.IRestaurantGateway
import kotlin.math.ceil
import kotlin.math.floor

class RestaurantFakeGateway : IRestaurantGateway {

    override suspend fun getRestaurants(
        pageNumber: Int,
        numberOfRestaurantsInPage: Int,
        restaurantName: String,
        rating: Double?,
        priceLevel: Int?,
    ): DataWrapper<Restaurant> {
        var restaurants = restaurants.toEntity()
        if (restaurantName.isNotEmpty()) {
            restaurants = restaurants.filter {
                it.name.startsWith(
                    restaurantName,
                    true
                )
            }
        }
        if (rating != null && priceLevel != null) {
            restaurants = restaurants.filter {
                it.priceLevel == priceLevel &&
                        when {
                            rating.rem(1) > 0.89 || rating.rem(1) == 0.0 || rating.rem(1) > 0.5
                            -> it.rating in floor(rating) - 0.1..0.49 + floor(rating)

                            else -> it.rating in 0.5 + floor(rating)..0.89 + floor(rating)
                        }
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
            ownerUsername = restaurant.ownerUsername,
            phoneNumber = restaurant.phoneNumber,
            workingHours = restaurant.workingHours,
            rating = 3.0,
            priceLevel = 1
        )
    }

    override suspend fun deleteRestaurant(id: String): Boolean {
        restaurants.remove(restaurants.find { it.id == id })
        return true
    }

    override suspend fun getCuisines(): List<String> {
        return cuisines
    }

    override suspend fun createCuisine(cuisineName: String): String {
        return if (cuisineName !in cuisines && cuisineName.isNotBlank()) {
            cuisines.add(0, cuisineName)
            cuisineName
        } else ""
    }

    override suspend fun deleteCuisine(cuisineName: String): String {
        cuisines.remove(cuisineName)
        return cuisineName
    }

    private val cuisines = mutableListOf<String>(
        "Angolan cuisine",
        "Cameroonian cuisine",
        "Chadian cuisine",
        "Congolese cuisine",
        "Centrafrican cuisine",
        "Equatorial Guinea cuisine",
        "Gabonese cuisine",
        "Santomean cuisine",
        "Burundian cuisine",
        "Djiboutian cuisine",
        "Eritrean cuisine",
        "Ethiopian cuisine",
        "Kenyan cuisine",
        "Maasai cuisine",
        "Rwandan cuisine",
        "Somali cuisine",
        "South Sudanese cuisine",
        "Tanzanian cuisine",
        "Zanzibari cuisine",
        "Ugandan cuisine",
    )

    private val restaurants = mutableListOf<RestaurantDto>(
        RestaurantDto(
            id = "8c90c4c6-1e69-47f3-aa59-2edcd6f0057b",
            name = "Mujtaba Restaurant",
            ownerUsername = "mujtaba",
            phoneNumber = "0532465722",
            rating = 0.4,
            priceLevel = 1,
            workingHours = "06:30 - 22:30"
        ),
        RestaurantDto(
            id = "6e21s4f-aw32-fs3e-fe43-aw56g4yr324",
            name = "Karrar Restaurant",
            ownerUsername = "karrar",
            phoneNumber = "0535232154",
            rating = 3.5,
            priceLevel = 1,
            workingHours = "12:00 - 23:00"
        ),
        RestaurantDto(
            id = "7a33sax-aw32-fs3e-12df-42ad6x352zse",
            name = "Saif Restaurant",
            ownerUsername = "saif",
            phoneNumber = "0554627893",
            rating = 4.0,
            priceLevel = 3,
            workingHours = "09:00 - 23:00"
        ),
        RestaurantDto(
            id = "7y1z47c-s2df-76de-dwe2-42ad6x352zse",
            name = "Nada Restaurant",
            ownerUsername = "nada",
            phoneNumber = "0524242766",
            rating = 3.4,
            priceLevel = 2,
            workingHours = "01:00 - 23:00"
        ),
        RestaurantDto(
            id = "3e1f5d4a-8317-4f13-aa89-2c094652e6a3",
            name = "Asia Restaurant",
            ownerUsername = "asia",
            phoneNumber = "0528242165",
            rating = 2.9,
            priceLevel = 1,
            workingHours = "09:30 - 21:30"
        ),
        RestaurantDto(
            id = "7a1bfe39-4b2c-4f76-bde0-82da2eaf9e99",
            name = "Kamel Restaurant",
            ownerUsername = "kamel",
            phoneNumber = "0528242235",
            rating = 4.9,
            priceLevel = 3,
            workingHours = "06:30 - 22:30"
        ),
    )

}