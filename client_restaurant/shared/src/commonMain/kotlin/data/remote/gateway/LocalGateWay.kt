package data.remote.gateway

import data.remote.model.FlagDto
import data.remote.model.TokenDto
import domain.gateway.ILocalGateWay
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query


class LocalGateWay( private val realm: Realm):ILocalGateWay {

      override suspend fun saveAccessToken(token: String) {
        realm.write {
            copyToRealm(TokenDto().apply {
                this.accessToken = token
            })
        }
    }

    override suspend fun getAccessToken(): String {
        return realm.query<TokenDto>().first().find()?.accessToken
            ?: throw Exception("Access Token not found")
    }

    override suspend fun saveRefreshToken(token: String) {
        realm.write {
            copyToRealm(TokenDto().apply {
                this.refreshToken = token
            })
        }
    }
    override suspend fun getRefreshToken(): String {
        return realm.query<TokenDto>().first().find()?.refreshToken
            ?: throw Exception("Refresh Token not found")
    }

    override suspend fun saveLoggedInFlag(isChecked: Boolean) {
        realm.write {
            copyToRealm(FlagDto().apply {
                this.isRememberMeChecked = isChecked
            })
        }
    }

    override suspend fun getLoggedInFlag(): Boolean {
        return realm.query<FlagDto>().first().find()?.isRememberMeChecked
            ?: throw Exception("Nothing ..")
    }


}