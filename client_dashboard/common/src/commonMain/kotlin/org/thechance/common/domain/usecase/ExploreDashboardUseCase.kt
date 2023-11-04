package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.Country
import org.thechance.common.domain.entity.Cuisine
import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.LocationInfo
import org.thechance.common.domain.entity.Offer
import org.thechance.common.domain.entity.Permission
import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.entity.RevenueShare
import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.entity.TaxiFiltration
import org.thechance.common.domain.entity.TotalRevenueShare
import org.thechance.common.domain.entity.User
import org.thechance.common.domain.getway.ILocationGateway
import org.thechance.common.domain.getway.IRemoteGateway
import org.thechance.common.domain.getway.IRestaurantGateway
import org.thechance.common.domain.getway.ITaxisGateway
import org.thechance.common.domain.getway.IUserLocalGateway
import org.thechance.common.domain.getway.IUsersGateway
import org.thechance.common.domain.util.RevenueShareDate

interface IExploreDashboardUseCase {

    suspend fun getCurrentLocation(): LocationInfo

    suspend fun getRestaurant(
        pageNumber: Int,
        numberOfRestaurantsInPage: Int,
        restaurantName: String,
        rating: Double,
        priceLevel: Int
    ): DataWrapper<Restaurant>

    suspend fun getRestaurantById(id: String): Restaurant

    suspend fun getRevenueShare(revenueShareDate: RevenueShareDate): TotalRevenueShare
    suspend fun getDashboardRevenueShare(): RevenueShare
    suspend fun getCuisines(): List<Cuisine>
    suspend fun getOffers(): List<Offer>



    suspend fun getTaxis(
        username: String?,
        taxiFiltration: TaxiFiltration,
        page: Int,
        limit: Int
    ): DataWrapper<Taxi>

    suspend fun getTaxiById(id: String): Taxi


    suspend fun getUserInfo(): String

    suspend fun getLastRegisteredUsers(limit: Int = 4): List<User>

    suspend fun getUsers(
        query: String? = null,
        byPermissions: List<Permission>,
        byCountries: List<Country>,
        page: Int,
        numberOfUsers: Int
    ): DataWrapper<User>

}

class ExploreDashboardUseCase(
    private val restaurantGateway: IRestaurantGateway,
    private val remoteGateway: IRemoteGateway,
    private val taxiGateway: ITaxisGateway,
    private val userGateway: IUsersGateway,
    private val userLocalGateway: IUserLocalGateway,
    private val locationGateway: ILocationGateway
) : IExploreDashboardUseCase {

    override suspend fun getCurrentLocation(): LocationInfo {
        return locationGateway.getCurrentLocation()
    }

    override suspend fun getRestaurant(
        pageNumber: Int,
        numberOfRestaurantsInPage: Int,
        restaurantName: String,
        rating: Double,
        priceLevel: Int
    ): DataWrapper<Restaurant> {
        return restaurantGateway.getRestaurants(
            pageNumber,
            numberOfRestaurantsInPage,
            restaurantName,
            rating,
            priceLevel
        )
    }

    override suspend fun getRestaurantById(id: String): Restaurant {
        return restaurantGateway.getRestaurantById(id)
    }

    override suspend fun getRevenueShare(revenueShareDate: RevenueShareDate): TotalRevenueShare {
        return remoteGateway.getRevenueShare(revenueShareDate)
    }
    override suspend fun getDashboardRevenueShare(): RevenueShare {
        return remoteGateway.getDashboardRevenueShare()
    }


    override suspend fun getCuisines(): List<Cuisine> {
        return restaurantGateway.getCuisines()
    }
    override suspend fun getOffers(): List<Offer> {
        return restaurantGateway.getOffers()
    }



    override suspend fun getTaxis(
        username: String?,
        taxiFiltration: TaxiFiltration,
        page: Int,
        limit: Int
    ): DataWrapper<Taxi> {
        return taxiGateway.getTaxis(
            page = page,
            limit = limit,
            username = username,
            taxiFiltration = taxiFiltration
        )
    }

    override suspend fun getTaxiById(id: String): Taxi {
        return taxiGateway.getTaxiById(id)
    }
    override suspend fun getUsers(
        query: String?,
        byPermissions: List<Permission>,
        byCountries: List<Country>,
        page: Int,
        numberOfUsers: Int,
    ): DataWrapper<User> {
        return userGateway.getUsers(query, byPermissions, byCountries, page, numberOfUsers)
    }

    override suspend fun getLastRegisteredUsers(limit: Int): List<User> {
        return userGateway.getLastRegisteredUsers(limit)
    }

    override suspend fun getUserInfo(): String {
        return userLocalGateway.getUserName()
    }



}
