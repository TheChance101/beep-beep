package data.gateway.remote

import data.remote.model.BaseResponse
import data.remote.model.UserTokensDto
import domain.gateway.remote.IRemoteIdentityGateway
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.http.Parameters


class RemoteIdentityGateway(client: HttpClient) : IRemoteIdentityGateway,
    BaseRemoteGateway(client = client) {

    override suspend fun loginUser(userName: String, password: String): Pair<String, String> {
        val result = tryToExecute<BaseResponse<UserTokensDto>>() {
            submitForm(
                formParameters = Parameters.build {
                    append("username", userName)
                    append("password", password)
                }
            ) {
                url("login")
                header("Accept-Language", "ar")
                header("Country-Code", "EG")
            }
        }.value ?: throw Exception()

        return Pair(result.accessToken, result.refreshToken)
    }
}