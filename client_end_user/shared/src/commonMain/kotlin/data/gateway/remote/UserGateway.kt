package data.gateway.remote

import data.remote.mapper.toEntity
import data.remote.mapper.toSessionEntity
import data.remote.model.RestaurantDto
import data.remote.model.ServerResponse
import data.remote.model.SessionDto
import data.remote.model.UserDetailsDto
import domain.entity.Account
import domain.entity.Restaurant
import domain.entity.Session
import domain.entity.User
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

    override suspend fun createUser(userCreation: Account): User {
        return tryToExecute<ServerResponse<UserDetailsDto>> {
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
        }.value?.toEntity()
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

    override suspend fun getUserProfile(): User {
        return tryToExecute<ServerResponse<UserDetailsDto>> {
            get("/user")
        }.value?.toEntity()
            ?: throw AuthorizationException.InvalidCredentialsException("Invalid Credential")
    }

    override suspend fun updateProfile(fullName: String?, phone: String?): User {
        return tryToExecute<ServerResponse<UserDetailsDto>> {
            submitForm(
                url = ("/user/profile"),
                formParameters = Parameters.build {
                    fullName?.let { append("fullName", it) }
                    phone?.let { append("phone", it) }
                }
            ) {
                method = HttpMethod.Put
            }
        }.value?.toEntity()
            ?: throw AuthorizationException.InvalidCredentialsException("Invalid Credential")

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
}
