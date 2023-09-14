package org.thechance.common.data.fake_gateway

import org.thechance.common.domain.gateway.IIdentityGateway

class IdentityFakeGateway : IIdentityGateway {
    override suspend fun loginUser(username: String, password: String): Pair<String, String> {
        return Pair("", "")
    }

    override suspend fun saveRefreshToken(token: String) {
        //do nothing
    }

    override suspend fun saveAccessToken(token: String) {
        //do nothing
    }

    override suspend fun refreshTokens(refreshToken: String): Pair<String, String> {
        return Pair("", "")
    }

    override suspend fun shouldUserKeptLoggedIn(keepLoggedIn: Boolean) {
        //do nothing
    }

    override suspend fun isUserKeptLoggedIn(): Boolean {
        return false
    }

    override suspend fun createUserConfiguration() {
        //do nothing
    }
}