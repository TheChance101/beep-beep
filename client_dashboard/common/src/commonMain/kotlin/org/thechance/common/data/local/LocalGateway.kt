package org.thechance.common.data.local


import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import org.thechance.common.data.local.local_dto.KeepUserLoggedInLocalDto
import org.thechance.common.data.local.local_dto.TokenLocalDto
import org.thechance.common.data.local.local_dto.TokenType
import org.thechance.common.domain.getway.ILocalGateway

class LocalGateway(private val realm: Realm) : ILocalGateway {

    override suspend fun saveAccessToken(token: String) {
        realm.write {
            copyToRealm(TokenLocalDto().apply {
                this.token = token
                this.type = TokenType.ACCESS_TOKEN.name
            })
        }
    }

    override suspend fun saveRefreshToken(token: String) {
        realm.write {
            copyToRealm(TokenLocalDto().apply {
                this.token = token
                this.type = TokenType.REFRESH_TOKEN.name
            })
        }
    }

    override suspend fun getAccessToken(): String {
        return realm.query<TokenLocalDto>(
            "type == ${TokenType.ACCESS_TOKEN.name}"
        ).first().find()?.token
            ?: throw Exception("Token not found")
    }

    override suspend fun getRefreshToken(): String {
        return realm.query<TokenLocalDto>(
            "type == ${TokenType.REFRESH_TOKEN.name}"
        ).first().find()?.token
            ?: throw Exception("Token not found")
    }

    override suspend fun clearTokens() {
        realm.write { delete(query<TokenLocalDto>()) }
    }

    override suspend fun shouldUserKeptLoggedIn(keepLoggedIn: Boolean) {
        realm.write { KeepUserLoggedInLocalDto().apply { this.keepLoggedIn = keepLoggedIn } }
    }

}