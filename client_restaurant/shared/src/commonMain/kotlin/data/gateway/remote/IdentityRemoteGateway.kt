package data.gateway.remote

import data.remote.mapper.toEntity
import data.remote.model.BaseResponse
import data.remote.model.RestaurantRequestPermissionDto
import data.remote.model.SessionDto
import data.remote.model.TripDto
import data.remote.model.UserTokensDto
import data.service.IFirebaseMessagingService
import domain.entity.RestaurantRequestPermission
import domain.entity.Session
import domain.gateway.remote.IIdentityRemoteGateway
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.Parameters
import io.ktor.util.InternalAPI
import kotlinx.serialization.json.Json
import presentation.base.InvalidCredentialsException

class IdentityRemoteGateway(
    client: HttpClient,/*, private val firebaseService: IFirebaseMessagingService*/
) : IIdentityRemoteGateway,
    BaseRemoteGateway(client = client) {
    override suspend fun getDeviceToken(): String {
//        return firebaseService.getDeviceToken()
        TODO()
    }

    override suspend fun loginUser(userName: String, password: String): Session {
        val result = tryToExecute<BaseResponse<SessionDto>> {
            submitForm(
                url = ("/login"),
                formParameters = Parameters.build {
                    append("username", userName)
                    append("password", password)
//                    append("token", deviceToken)
                }
            )
        }.value

        return result?.toEntity() ?: throw InvalidCredentialsException()
    }

    override suspend fun refreshAccessToken(refreshToken: String): Pair<String, String> {
        val result = tryToExecute<BaseResponse<UserTokensDto>> {
            submitForm(
                formParameters = Parameters.build {
                    append("refreshToken", refreshToken)
                }
            ) {
                url("refresh-access-token")
            }
        }.value ?: throw Exception()

        return Pair(result.accessToken, result.refreshToken)
    }

    @OptIn(InternalAPI::class)
    override suspend fun createRequestPermission(
        restaurantRequestPermission: String,
        ownerEmail: String,
        description: String,
    ): Boolean {
       return tryToExecute<BaseResponse<RestaurantRequestPermissionDto>> {
            val restaurantRequestDto = RestaurantRequestPermissionDto(
                restaurantName = restaurantRequestPermission,
                ownerEmail = ownerEmail,
                cause = description
            )
            post("/permission/restaurant") {
                body = Json.encodeToString(RestaurantRequestPermissionDto.serializer(), restaurantRequestDto)
            }
        }.isSuccess

    }
}

private fun RestaurantRequestPermission.toDto(): RestaurantRequestPermissionDto {

    return RestaurantRequestPermissionDto(
        restaurantName = restaurantName,
        ownerEmail = ownerEmail,
        cause = cause
    )
}
