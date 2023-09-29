package data.gateway.remote

import data.remote.mapper.toSessionEntity
import data.remote.mapper.toUser
import data.remote.model.ServerResponse
import data.remote.model.SessionDto
import data.remote.model.UserDto
import domain.entity.Session
import domain.entity.User
import domain.entity.UserCreation
import domain.gateway.IUserRemoteGateway
import domain.utils.AuthorizationException
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters

class UserRemoteRemoteGateway(client: HttpClient) : BaseGateway(client), IUserRemoteGateway {

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
                    append("phone", userCreation.phone) // todo: remove this todo when phone is added to the backend
                    append("address", userCreation.address) // todo: remove this todo when address is added to the backend
                }
            ){
                method = HttpMethod.Post
            }
        }.value?.toUser() ?: throw AuthorizationException.InvalidCredentialsException("Invalid Credential")
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
                headers.append("Application-Id", "1000") // todo: remove this line in next deploy when the backend is updated
                method = HttpMethod.Post
            }
        }.value?.toSessionEntity() ?: throw AuthorizationException.InvalidCredentialsException("Invalid Credential")
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