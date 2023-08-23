package data.gateway

import data.remote.model.TokenDto
import domain.gateway.IRestaurantGateWay
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query

class RestaurantGateWay (
    private val realm: Realm
): IRestaurantGateWay {

    override suspend fun saveAccessToken(token: String) {
        realm.write {
            copyToRealm(TokenDto().apply {
                this.accessToken = token
            })
        }
    }

    override suspend fun saveRefreshToken(token: String) {
        realm.write {
            copyToRealm(TokenDto().apply {
                this.refreshToken = token
            })
        }
    }

    override suspend fun getAccessToken(): String {
        return realm.query<TokenDto>().first().find()?.accessToken ?: throw Exception("Access Token not found")
    }

    override suspend fun getRefreshToken(): String {
        return realm.query<TokenDto>().first().find()?.refreshToken ?: throw Exception("Refresh Token not found")
    }

}