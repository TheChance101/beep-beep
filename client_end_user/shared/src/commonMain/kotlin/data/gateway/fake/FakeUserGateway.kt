package data.gateway.fake

import domain.entity.Session
import domain.entity.User
import domain.entity.UserCreation
import domain.entity.UserDetails
import domain.gateway.IUserGateway

class FakeUserGateway : IUserGateway {
    override suspend fun createUser(userCreation: UserCreation): User {
        TODO("Not yet implemented")
    }

    override suspend fun loginUser(username: String, password: String): Session {
        TODO("Not yet implemented")
    }

    override suspend fun refreshAccessToken(refreshToken: String): Pair<String, String> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserProfile(): UserDetails {
        TODO("Not yet implemented")
    }

    override suspend fun getUserWallet(): User {
        return User(name = "Test", currency = "$", walletValue = 100.0)
    }
}