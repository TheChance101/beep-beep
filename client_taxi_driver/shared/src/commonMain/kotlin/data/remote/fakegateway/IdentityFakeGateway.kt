package data.remote.fakegateway

import domain.InvalidPasswordException
import domain.InvalidUserNameException
import domain.PermissionDenied
import domain.entity.Session
import domain.entity.Taxi
import domain.entity.TaxiRequestPermission
import domain.gateway.remote.IIdentityRemoteGateway

class IdentityFakeGateway : IIdentityRemoteGateway {

    override suspend fun loginUser(userName: String, password: String): Session {
        if (userName == "fake" && password == "Fake1234") throw PermissionDenied()

        if (userName != "theChance") {
            throw InvalidUserNameException("Invalid UserName")
        }
        if (password != "theChance23") {
            throw InvalidPasswordException("Invalid Password")
        }
        return Session(
            accessToken = "wertqyhgt",
            refreshToken = "qazswxza",
        )
    }

    override suspend fun refreshAccessToken(refreshToken: String): Pair<String, String> {
        return Pair("wertqyhgt", "qazswxza")
    }

    override suspend fun createRequestPermission(taxiRequestPermission: TaxiRequestPermission): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getAllVehicles(): List<Taxi> {
        TODO("Not yet implemented")
    }


}