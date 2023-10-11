package data.gateway.remote

import data.remote.mapper.toEntity
import data.remote.mapper.toSessionEntity
import data.remote.mapper.toUser
import data.remote.model.LocationDto
import data.remote.model.RestaurantDto
import data.remote.model.ServerResponse
import data.remote.model.SessionDto
import data.remote.model.UserDetailsDto
import data.remote.model.UserDto
import domain.entity.Restaurant
import domain.entity.Session
import domain.entity.User
import domain.entity.UserCreation
import domain.entity.UserDetails
import domain.gateway.IUserGateway
import domain.utils.AuthorizationException
import domain.utils.GeneralException
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters

class UserGateway(client: HttpClient) : BaseGateway(client), IUserGateway {

    override suspend fun createUser(
        userCreation: UserCreation,
    ): User {
        return tryToExecute<ServerResponse<UserDto>> {
            submitForm(
                url = ("/signup"),
                formParameters = Parameters.build {
                    append("fullName", userCreation.fullName)
                    append("username", userCreation.username)
                    append("password", userCreation.password)
                    append("email", userCreation.email)
                    append(
                        "phone",
                        userCreation.phone
                    ) // todo: remove this todo when phone is added to the backend
                    append(
                        "address",
                        userCreation.address
                    ) // todo: remove this todo when address is added to the backend
                }
            ) {
                method = HttpMethod.Post
            }
        }.value?.toUser()
            ?: throw AuthorizationException.InvalidCredentialsException("Invalid Credential")
    }

    override suspend fun loginUser(username: String, password: String): Session {
        return tryToExecute<ServerResponse<SessionDto>> {
            submitForm(
                url = ("/login"),
                formParameters = Parameters.build {
                    append("username", username)
                    append("password", password)
                }
            ) {
                headers.append(
                    "Application-Id",
                    "1000"
                ) // todo: remove this line in next deploy when the backend is updated
                method = HttpMethod.Post
            }
        }.value?.toSessionEntity()
            ?: throw AuthorizationException.InvalidCredentialsException("Invalid Credential")
    }

    override suspend fun refreshAccessToken(refreshToken: String): Pair<String, String> {
        val result = tryToExecute<ServerResponse<SessionDto>> {
            submitForm {
                url("/refresh-access-token")
            }
        }.value ?: throw Exception()

        return Pair(result.accessToken, result.refreshToken)
    }

    override suspend fun getUserProfile(): UserDetails {
        return tryToExecute<ServerResponse<UserDetailsDto>> {
            get("/user")
        }.value?.toEntity()
            ?: throw AuthorizationException.InvalidCredentialsException("Invalid Credential")
    }

    override suspend fun getUserWallet(): User {
        // todo : implement this when the backend is ready
        return User(name = "Test", currency = "$", walletValue = 100.0)
    }

    override suspend fun getFavoriteRestaurants(): List<Restaurant> {
        return tryToExecute<ServerResponse<List<RestaurantDto>>> {
            get("/user/favorite")
        }.value?.map { it.toEntity() } ?: throw GeneralException.NotFoundException
    }

    override suspend fun addRestaurantToFavorites(restaurantId: String): Boolean {
        return tryToExecute<ServerResponse<Boolean>> {
            submitForm(
                url = ("/user/favorite"),
                formParameters = Parameters.build {
                    append("restaurantId", restaurantId)
                }
            ) {
                method = HttpMethod.Post
            }
        }.value ?: throw GeneralException.NotFoundException
    }

    override suspend fun removeRestaurantFromFavorites(restaurantId: String): Boolean {
        return tryToExecute<ServerResponse<Boolean>> {
            submitForm(
                url = ("/user/favorite"),
                formParameters = Parameters.build {
                    append("restaurantId", restaurantId)
                }
            ) {
                method = HttpMethod.Delete
            }
        }.value ?: throw GeneralException.NotFoundException
    }

    // Should be removed when the backend is ready
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
}