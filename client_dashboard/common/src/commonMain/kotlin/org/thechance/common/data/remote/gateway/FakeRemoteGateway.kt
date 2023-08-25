package org.thechance.common.data.remote.gateway


import org.thechance.common.data.local.LocalGateway
import org.thechance.common.data.remote.mapper.toDto
import org.thechance.common.data.remote.mapper.toEntity
import org.thechance.common.data.remote.model.AdminDto
import org.thechance.common.data.remote.model.DataWrapperDto
import org.thechance.common.data.remote.model.TaxiDto
import org.thechance.common.data.remote.model.UserDto
import org.thechance.common.data.remote.model.toEntity
import org.thechance.common.data.service.IFakeService
import org.thechance.common.domain.entity.*
import org.thechance.common.domain.getway.IRemoteGateway
import java.util.UUID
import kotlin.math.ceil
import kotlin.math.floor

class FakeRemoteGateway(
    private val fakeService: IFakeService,
    private val localGateway: LocalGateway
) : IRemoteGateway {
    override fun getUserData(): Admin =
        AdminDto(fullName = "asia").toEntity()

    override fun getUsers(page: Int, numberOfUsers: Int): DataWrapper<User> {
        val users = listOf(
            UserDto(
                id = "c4425a0e-9f0a-4df1-bcc1-6dd96322a990",
                fullName = "mohammed sayed",
                username = "mohammed_sayed",
                email = "elzamalk@example.com",
                country = "Egypt",
                permissions = listOf(
                    UserDto.PermissionDto(id = 3, permission = "END_USER"),
                    UserDto.PermissionDto(id = 1, permission = "RESTAURANT"),
                    UserDto.PermissionDto(id = 2, permission = "DRIVER"),
                    UserDto.PermissionDto(id = 1, permission = "SUPPORT"),
                    UserDto.PermissionDto(id = 2, permission = "ADMIN")
                )
            ),
            UserDto(
                id = "f7b087da-8c02-417b-a3db-54c82b5ff5b4",
                fullName = "asia",
                username = "asia",
                email = "asia@example.com",
                country = "Iraq",
                permissions = listOf(
                    UserDto.PermissionDto(id = 3, permission = "END_USER"),
                    UserDto.PermissionDto(id = 1, permission = "SUPPORT"),
                    UserDto.PermissionDto(id = 2, permission = "ADMIN"),
                )
            ),
            UserDto(
                id = "3e1f5d4a-8317-4f13-aa89-2c094652e6a3",
                fullName = "ali",
                username = "ali_jamal",
                email = "ali_jamal@example.com",
                country = "Iraq",
                permissions = listOf(
                    UserDto.PermissionDto(id = 3, permission = "END_USER"),
                    UserDto.PermissionDto(id = 1, permission = "ADMIN")
                )
            ),
            UserDto(
                id = "c3d8fe2b-6d36-47ea-964a-57d45e780bce",
                fullName = "mustafa",
                username = "mustafa_246",
                email = "mustafa_246@example.com",
                country = "Syria",
                permissions = listOf(
                    UserDto.PermissionDto(id = 1, permission = "END_USER"),
                    UserDto.PermissionDto(id = 2, permission = "RESTAURANT")
                )
            ),
            UserDto(
                id = "7a1bfe39-4b2c-4f76-bde0-82da2eaf9e99",
                fullName = "sarah ali",
                username = "sarah_ali_567",
                email = "sarah_ali_567@example.com",
                country = "Palestine",
                permissions = listOf(
                    UserDto.PermissionDto(id = 3, permission = "END_USER"),
                    UserDto.PermissionDto(id = 1, permission = "SUPPORT"),
                    UserDto.PermissionDto(id = 2, permission = "ADMIN"),
                    UserDto.PermissionDto(id = 3, permission = "DELIVERY")
                )
            ),
            UserDto(
                id = "8c90c4c6-1e69-47f3-aa59-2edcd6f0057b",
                fullName = "Jane Davis",
                username = "jane_davis_890",
                email = "jane_davis@example.com",
                country = "Other",
                permissions = listOf(
                    UserDto.PermissionDto(id = 1, permission = "END_USER"),
                    UserDto.PermissionDto(id = 2, permission = "DRIVER")
                )
            ),
        ).toEntity()
        val startIndex = (page - 1) * numberOfUsers
        val endIndex = startIndex + numberOfUsers
        val numberOfPages = ceil(users.size / (numberOfUsers * 1.0)).toInt()
        return try {
            DataWrapperDto(
                totalPages = numberOfPages,
                result = users.subList(startIndex, endIndex.coerceAtMost(users.size)),
                totalResult = users.size
            ).toEntity()
        } catch (e: Exception) {
            DataWrapperDto(
                totalPages = numberOfPages,
                result = users,
                totalResult = users.size
            ).toEntity()
        }
    }

    override suspend fun getTaxis(): List<Taxi> {
        return fakeService.getTaxis().toEntity()
    }


    override suspend fun createTaxi(taxi: AddTaxi): Taxi {
        val taxiDto = taxi.toDto()
       return fakeService.addTaxi(TaxiDto(
                id = UUID.randomUUID().toString(),
                plateNumber = taxiDto.plateNumber,
                color = taxiDto.color,
                type = taxiDto.type,
                seats = taxiDto.seats,
                username = taxiDto.username,
            )
        ).toEntity()
    }

    override suspend fun findTaxiByUsername(username: String): List<Taxi> {
        return fakeService.findTaxisByUsername(username).toEntity()
    }

    override suspend fun getPdfTaxiReport() {
        val taxiReportFile = fakeService.getTaxiPDFReport()
        localGateway.saveTaxiReport(taxiReportFile)
    }

    override suspend fun getRestaurants(): List<Restaurant> {
        return fakeService.getRestaurants().toEntity()
    }

    override suspend fun searchRestaurantsByRestaurantName(restaurantName: String): List<Restaurant> {
        return fakeService.searchRestaurantsByRestaurantName(restaurantName).toEntity()
    }

    override suspend fun filterRestaurants(rating: Double, priceLevel: Int): List<Restaurant> {
        return filterRestaurants(getRestaurants(), rating, priceLevel)
    }

    override suspend fun searchFilterRestaurants(
        restaurantName: String,
        rating: Double,
        priceLevel: Int
    ): List<Restaurant> {
        return filterRestaurants(
            searchRestaurantsByRestaurantName(restaurantName),
            rating,
            priceLevel
        )
    }

    override suspend fun loginUser(username: String, password: String): UserTokens {
        return UserTokens("", "")
    }

    private fun filterRestaurants(
        restaurants: List<Restaurant>,
        rating: Double,
        priceLevel: Int
    ): List<Restaurant> {
        return restaurants.filter {
            it.priceLevel == priceLevel &&
                    when {
                        rating.rem(1) > 0.89 || rating.rem(1) == 0.0 || rating.rem(1) > 0.5
                        -> it.rating in floor(rating) - 0.1..0.49 + floor(rating)

                        else -> it.rating in 0.5 + floor(rating)..0.89 + floor(rating)
                    }
        }
    }

    override suspend fun createRestaurant(restaurant: AddRestaurant): Restaurant {
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

    override suspend fun getCurrentLocation(): Location {
        return Location(location = "30.044420,31.235712")
    }
}