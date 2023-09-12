package data.gateway.remote

import data.remote.mapper.toEntity
import data.remote.model.BaseResponse
import data.remote.model.SessionDto
import domain.entity.Session
import domain.gateway.remote.IIdentityRemoteGateway
import domain.utils.InvalidCredentialsException
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters

class IdentityRemoteGateway(client: HttpClient) : IIdentityRemoteGateway,
    BaseRemoteGateway(client = client) {

    override suspend fun loginUser(userName: String, password: String): Session {
        val result = tryToExecute<BaseResponse<SessionDto>> {
            submitForm(
                url = ("/login"),
                formParameters = Parameters.build {
                    append("username", userName)
                    append("password", password)
                }
            ){
                method = HttpMethod.Post
            }
        }.value

        return result?.toEntity() ?: throw InvalidCredentialsException("Invalid Credential")
    }

    override suspend fun refreshAccessToken(refreshToken: String): Pair<String, String> {
        val result = tryToExecute<BaseResponse<SessionDto>> {
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

    override suspend fun createRequestPermission(
        restaurantName: String,
        ownerEmail: String,
        description: String
    ): Boolean {
        val result = tryToExecute<BaseResponse<Boolean>> {
            post("/restaurant-permission-request") {
                post("/meal") { setBody(restaurantName) }
            }
        }.value ?: throw Exception()

        return result
    }

}