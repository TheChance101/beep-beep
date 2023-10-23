package data.remote.gateway

import data.remote.mapper.toEntity
import data.remote.model.BaseResponse
import data.remote.model.SessionDto
import data.remote.model.UserTokensDto
import domain.InvalidCredentialsException
import domain.entity.Session
import domain.gateway.remote.IIdentityRemoteGateway
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.url
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
            )
        }.value

        return result?.toEntity() ?: throw InvalidCredentialsException("Invalid Credential")
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

    override suspend fun createRequestPermission(
        driverFullName: String,
        driverEmail: String,
        description: String,
    ) {
        // TODO: add create request permission end point when backend is done
    }
}