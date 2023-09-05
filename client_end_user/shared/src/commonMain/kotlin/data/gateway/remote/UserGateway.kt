package data.gateway.remote

import data.remote.mapper.toSessionEntity
import data.remote.model.ServerResponse
import data.remote.model.SessionDto
import domain.entity.Session
import domain.gateway.IUserGateway
import domain.utils.InvalidCredentialsException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.http.isSuccess
import io.ktor.http.parameters

class UserGateway(client: HttpClient) : BaseGateway(client), IUserGateway {

    override suspend fun createUser(
        fullName: String,
        username: String,
        password: String,
        email: String
    ): Boolean {
        try {
            val response = client.submitForm(
                formParameters = parameters {
                    append("fullName", fullName)
                    append("username", username)
                    append("password", password)
                    append("email", email)
                }
            ) {
                url("/signup")
                header("Accept-Language", "en")
            }
            val responseBody = response.body<ServerResponse<Boolean>>()
            if (response.status.isSuccess()) {
                return responseBody.value ?: false
            } else {
                throw Exception(responseBody.status.errorMessages.toString())
            }
        } catch (exception: Exception) {
            throw Exception(exception.message)
        }
    }

    override suspend fun loginUser(username: String, password: String): Session {
        return tryToExecute<ServerResponse<SessionDto>> {
            submitForm(
                url = ("/login"),
                formParameters = Parameters.build {
                    append("username", username)
                    append("password", password)
                }
            ){
                method = HttpMethod.Post
            }
        }.value?.toSessionEntity() ?: throw InvalidCredentialsException("Invalid Credential")
    }

    override suspend fun refreshAccessToken(refreshToken: String): Pair<String,String> {
        val result = tryToExecute<ServerResponse<SessionDto>> {
            submitForm {
                url("/refresh-access-token")
            }
        }.value ?: throw Exception()

        return Pair(result.accessToken,result.refreshToken)
    }
}