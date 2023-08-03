package org.thechance.service_restaurant.usecase.gateway

import org.thechance.service_restaurant.entity.Address
import org.thechance.service_restaurant.entity.Category
import org.thechance.service_restaurant.entity.Restaurant

interface RestaurantGateway {

    //region Address
    suspend fun getAddresses(): List<Address>
    suspend fun getAddress(id: String): Address?
    suspend fun addAddress(address: Address): Boolean
    suspend fun updateAddress(address: Address): Boolean
    suspend fun deleteAddress(id: String): Boolean

    suspend fun addAddressesToRestaurant(restaurantId: String, addressesIds: List<String>): Boolean
    suspend fun getAddressesInRestaurant(restaurantId: String): List<Address>
    suspend fun deleteAddressesInRestaurant(restaurantId: String, addressesIds: List<String>): Boolean
    //endregion

    //region Category
    suspend fun getCategories(): List<Category>
    suspend fun getCategory(categoryId: String): Category?
    suspend fun addCategory(category: Category): Boolean
    suspend fun updateCategory(category: Category): Boolean
    suspend fun deleteCategory(categoryId: String): Boolean
    suspend fun addRestaurantsToCategory(categoryId: String, restaurantIds: List<String>): Boolean

    suspend fun getRestaurantsInCategory(categoryId: String): List<Restaurant>
    //endregion

    //region restaurant
    suspend fun getRestaurants(): List<Restaurant>
    suspend fun getRestaurant(id: String): Restaurant?
    suspend fun addRestaurant(restaurant: Restaurant): Boolean
    suspend fun updateRestaurant(restaurant: Restaurant): Boolean
    suspend fun deleteRestaurant(restaurantId: String): Boolean
    //endregion

}