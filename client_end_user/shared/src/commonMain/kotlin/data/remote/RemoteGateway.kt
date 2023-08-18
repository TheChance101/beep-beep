package data.remote

import data.mapper.toEntity
import data.remote.remoteDto.BaseResponse
import data.remote.remoteDto.TokensResponse
import domain.entity.Tokens
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
    ): Boolean {
        return client.submitForm("/user",
            formParameters = parameters {
                append("fullName", fullName)
                append("username", username)
                append("password", password)
                append("email", email)
            }
        ).body<BaseResponse<Boolean>>().value ?: false
    }

    override suspend fun loginUser(
        userName: String,
        password: String,
    ): Tokens {
        return client.submitForm("/user/login",
            formParameters = parameters {
                append("username", userName)
                append("password", password)
            }
        ).body<BaseResponse<TokensResponse>>().value?.toEntity() ?: throw Exception()
    }

}