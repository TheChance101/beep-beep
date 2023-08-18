package domain.gateway

import data.remote.remoteDto.BaseResponse
import data.remote.remoteDto.TokensResponse

interface IRemoteGateway {

    suspend fun createUser(
        fullName: String,
        username: String,
        password: String,
        email: String
    ) : BaseResponse<Boolean>

    suspend fun loginUser(
        userName: String,
        password: String,
    ):BaseResponse<TokensResponse>

}