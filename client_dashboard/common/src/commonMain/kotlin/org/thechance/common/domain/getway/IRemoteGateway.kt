package org.thechance.common.domain.getway

import org.thechance.common.domain.entity.*


interface IRemoteGateway {

    suspend fun getUserData(): String

    suspend fun getUsers(
        byPermissions: List<Permission>,
        byCountries: List<String>,
        page: Int,
        numberOfUsers: Int
    ): DataWrapper<User>

    suspend fun getTaxis(page: Int, numberOfTaxis: Int): DataWrapper<Taxi>

    suspend fun createTaxi(taxi: NewTaxiInfo): Taxi

    suspend fun searchTaxisByUsername(username: String, page: Int, numberOfTaxis: Int): DataWrapper<Taxi>

    suspend fun filterTaxis(taxi: TaxiFiltration, page: Int, numberOfTaxis: Int): DataWrapper<Taxi>

    suspend fun getPdfTaxiReport()

    suspend fun getRestaurants(
        pageNumber: Int,
        numberOfRestaurantsInPage: Int,
        restaurantName: String,
        rating: Double?,
        priceLevel: Int?
    ): DataWrapper<Restaurant>

    suspend fun loginUser(username: String, password: String): Pair<String, String>

    suspend fun createRestaurant(restaurant: AddRestaurant): Restaurant

    suspend fun getCurrentLocation(): Location

    suspend fun searchUsers(
        query: String,
        byPermissions: List<Permission>,
        byCountries: List<String>,
        page: Int,
        numberOfUsers: Int
    ): DataWrapper<User>

    suspend fun filterUsers(
        permissions: List<Permission>,
        countries: List<String>,
        page: Int,
        numberOfUsers: Int
    ): DataWrapper<User>

}