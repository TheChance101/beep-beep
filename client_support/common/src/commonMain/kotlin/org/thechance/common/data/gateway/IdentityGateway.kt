package org.thechance.common.data.gateway

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import org.thechance.common.data.local.model.UserConfigurationCollection
import org.thechance.common.data.remote.model.BaseResponse
import org.thechance.common.data.remote.model.UserTokensRemoteDto
import org.thechance.common.domain.gateway.IIdentityGateway

class IdentityGateway(private val client: HttpClient, private val realm: Realm) : IIdentityGateway, BaseGateway() {

    override suspend fun loginUser(username: String, password: String): Pair<String, String> {
        val result = tryToExecute<BaseResponse<UserTokensRemoteDto>>(client) {
            submitForm(
                formParameters = Parameters.build {
                    append("username", username)
                    append("password", password)
                }
            ) { url("/login") }
        }.value

        return Pair(result?.accessToken ?: "", result?.refreshToken ?: "")
    }

    override suspend fun saveRefreshToken(token: String) {
        realm.write {
            query<UserConfigurationCollection>("$ID == $CONFIGURATION_ID").first().find()?.refreshToken = token
        }
    }

    override suspend fun saveAccessToken(token: String) {
        realm.write {
            query<UserConfigurationCollection>("$ID == $CONFIGURATION_ID").first().find()?.accessToken = token
        }
    }

    override suspend fun refreshTokens(refreshToken: String): Pair<String, String> {
        return Pair("", "")
    }

    override suspend fun shouldUserKeptLoggedIn(keepLoggedIn: Boolean) {
        realm.write {
            query<UserConfigurationCollection>("$ID == $CONFIGURATION_ID").first().find()?.keepLoggedIn = keepLoggedIn
        }
    }

    override suspend fun isUserKeptLoggedIn(): Boolean {
        return realm.query<UserConfigurationCollection>(
            "$ID == $CONFIGURATION_ID"
        ).first().find()?.keepLoggedIn ?: false
    }

    override suspend fun createUserConfiguration() {
        realm.write { copyToRealm(UserConfigurationCollection().apply { id = CONFIGURATION_ID }) }
    }

    companion object {
        private const val CONFIGURATION_ID = 0
        private const val ID = "id"
    }

}