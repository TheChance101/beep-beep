package data.gateway.remote

import data.gateway.service.IFireBaseMessageService
import data.remote.mapper.toEntity
import data.remote.mapper.toSessionEntity
import data.remote.mapper.toUserRegistrationDto
import data.remote.model.AddressDto
import data.remote.model.NotificationHistoryDto
import data.remote.model.PaginationResponse
import data.remote.model.RestaurantDto
import data.remote.model.ServerResponse
import data.remote.model.SessionDto
import data.remote.model.UserDetailsDto
import data.remote.model.UserRegistrationDto
import domain.entity.Account
import domain.entity.Address
import domain.entity.NotificationHistory
import domain.entity.PaginationItems
import domain.entity.Restaurant
import domain.entity.Session
import domain.entity.User
import domain.gateway.IUserGateway
import domain.utils.AuthorizationException
import domain.utils.GeneralException
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.util.InternalAPI
import kotlinx.serialization.json.Json

class UserGateway(
    client: HttpClient,
    private val firebaseService: IFireBaseMessageService,
) : BaseGateway(client), IUserGateway {

    @OptIn(InternalAPI::class)
    override suspend fun createUser(account: Account): User {
        return tryToExecute<ServerResponse<UserDetailsDto>> {
            post("/signup") {
                val userRegistrationDto = account.toUserRegistrationDto()
                body = Json.encodeToString(UserRegistrationDto.serializer(), userRegistrationDto)
            }
        }.value?.toEntity()
            ?: throw AuthorizationException.InvalidCredentialsException("Invalid Credential")
    }

    override suspend fun loginUser(
        username: String, password: String, deviceToken: String,
    ): Session {
        return tryToExecute<ServerResponse<SessionDto>> {
            submitForm(
                url = ("/login"),
                formParameters = Parameters.build {
                    append("username", username)
                    append("password", password)
                    append("token", deviceToken)
                }
            ) {
                method = HttpMethod.Post
            }
        }.value?.toSessionEntity()
            ?: throw AuthorizationException.InvalidCredentialsException("Invalid Credential")
    }

    override suspend fun getDeviceToken(): String {
        return firebaseService.getDeviceToken()
    }

    override suspend fun refreshAccessToken(): Pair<String, String> {
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

    override suspend fun getUserAddresses(): List<Address> {
        val response = tryToExecute<ServerResponse<List<AddressDto>>> {
            get("/user/addresses")
        }
        if (response.isSuccess) {
            return response.value?.map { it.toEntity() }
                ?: throw GeneralException.NotFoundException
        } else {
            if (response.status.code == 404) {
                throw GeneralException.NotFoundException
            } else {
                throw GeneralException.UnknownErrorException
            }
        }
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

    override suspend fun getNotificationHistory(
        page: Int,
        limit: Int,
    ): PaginationItems<NotificationHistory> {
        val result = tryToExecute<ServerResponse<PaginationResponse<NotificationHistoryDto>>> {
            get("/notifications/history") {
                parameter("page", page)
                parameter("limit", limit)
            }
        }.value
        return paginateData(
            result = result?.items?.map { it.toEntity() } ?: emptyList(),
            page = page,
            total = limit.toLong(),
        )
    }

    override suspend fun getNotificationHistoryInLast24Hours(): List<NotificationHistory> {
        return tryToExecute<ServerResponse<List<NotificationHistoryDto>>> {
            get("/notifications/history-24hours")
        }.value?.map { it.toEntity() } ?: throw GeneralException.NotFoundException
    }
}
