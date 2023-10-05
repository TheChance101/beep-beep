package domain.usecase

import data.gateway.remote.UserGateway
import data.local.mapper.toFormattedString
import data.local.mapper.toPreferredRide
import domain.entity.PreferredRide
import domain.entity.User
import domain.entity.UserDetails
import domain.gateway.IUserGateway
import domain.gateway.local.ILocalConfigurationGateway
import domain.utils.AuthorizationException
import kotlinx.coroutines.flow.Flow

interface IManageUserUseCase {
    suspend fun getUserWallet(): User
    suspend fun saveIsFirstTimeUseApp(isFirstTimeUseApp: Boolean)
    suspend fun getIsFirstTimeUseApp(): Boolean
    suspend fun getUserLanguageCode(): Flow<String>
    suspend fun getUserProfile(): UserDetails
}

class ManageUserUseCase(
    private val localGateway: ILocalConfigurationGateway,
    private val userGateway: IUserGateway
) : IManageUserUseCase {

    override suspend fun getUserWallet(): User {
        return if (localGateway.getAccessToken().isNotEmpty()) {
            userGateway.getUserWallet()
        } else {
            throw AuthorizationException.UnAuthorizedException
        }
    }

    override suspend fun saveIsFirstTimeUseApp(isFirstTimeUseApp: Boolean) {
        return localGateway.saveIsFirstTimeUseApp(isFirstTimeUseApp)
    }

    override suspend fun getIsFirstTimeUseApp(): Boolean {
        return localGateway.getIsFirstTimeUseApp()
    }

    override suspend fun getUserLanguageCode(): Flow<String> {
        return localGateway.getLanguageCodeFlow()
    }

    override suspend fun getUserProfile(): UserDetails {
        return userGateway.getUserProfile()
    }
}
