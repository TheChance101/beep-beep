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
        try {
            val response = client.submitForm("/user",
                formParameters = parameters {
                    append("fullName", fullName)
                    append("username", username)
                    append("password", password)
                    append("email", email)
                }
            )
            val responseBody = response.body<BaseResponse<Boolean>>()
            if (response.status.isSuccess()) {
                return responseBody.value ?: false
            } else {
                throw Exception(responseBody.status.errorMessages.toString())
            }
        } catch (exception: Exception) {
            throw Exception(exception.message)
        }
    }

    override suspend fun loginUser(
        userName: String,
        password: String,
    ): Tokens {
        try {
            val response = client.submitForm("/user/login",
                formParameters = parameters {
                    append("username", userName)
                    append("password", password)
                }
            )
            val responseBody = response.body<BaseResponse<TokensResponse>>()
            if (response.status.isSuccess()) {
                return responseBody.value?.toEntity() ?: throw Exception()
            } else {
                throw Exception(responseBody.status.errorMessages.toString())
            }

        } catch (exception: Exception) {
            throw Exception(exception.message)
        }
    }

}