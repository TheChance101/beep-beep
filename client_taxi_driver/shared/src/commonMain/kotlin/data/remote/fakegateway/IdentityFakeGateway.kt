package data.remote.fakegateway

import domain.InvalidPasswordException
import domain.entity.Session
import domain.gateway.remote.IIdentityRemoteGateway

class IdentityFakeGateway: IIdentityRemoteGateway {

    override suspend fun loginUser(userName: String, password: String): Session {
        if (userName != "theChance") {
            throw InvalidPasswordException("Invalid UserName")
        }
        if (password != "theChance23") {
            throw InvalidPasswordException("Invalid Password")
        }
        return Session(
            accessToken = "wertqyhgt" ,
            refreshToken = "qazswxza",
        )
    }

    override suspend fun refreshAccessToken(refreshToken: String): Pair<String, String> {
        TODO("Not yet implemented")
    }

    override suspend fun createRequestPermission(
        taxiRequestPermission: String,
        driverEmail: String,
        description: String
    ): Boolean {
        TODO("Not yet implemented")
    }
}