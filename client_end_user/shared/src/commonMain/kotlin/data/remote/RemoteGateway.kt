package data.remote

import data.remote.remoteDto.BaseResponse
import data.remote.remoteDto.TokensResponse
import domain.gateway.IRemoteGateway
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.forms.*
import io.ktor.http.*

class RemoteGateway(private val client: HttpClient) : IRemoteGateway {

    override suspend fun createUser(
        fullName: String,
        username: String,
        password: String,
        email: String
    ): BaseResponse<Boolean> {
        return client.submitForm("/user",
            formParameters = parameters {
                append("fullName", fullName)
                append("username", username)
                append("password", password)
                append("email", email)
            }
        ).body()
    }

    override suspend fun loginUser(
        userName: String,
        password: String,
    ): BaseResponse<TokensResponse> {
        return client.submitForm("/user/login",
            formParameters = parameters {
                append("username", userName)
                append("password", password)
            }
        ).body()
    }

}