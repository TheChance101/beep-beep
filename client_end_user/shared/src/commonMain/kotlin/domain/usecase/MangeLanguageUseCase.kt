package domain.usecase

import domain.gateway.local.ILocalConfigurationGateway
import kotlinx.coroutines.flow.Flow

interface IMangeLanguageUseCase {
    suspend fun saveLanguageCode(code: String)
    suspend fun getLanguageCode(): Flow<String>
}

class MangeLanguageUseCase(
    private val localGateway: ILocalConfigurationGateway,
) : IMangeLanguageUseCase {
    override suspend fun saveLanguageCode(code: String) {
       return localGateway.saveLanguageCode(code)
    }

    override suspend fun getLanguageCode(): Flow<String> {
        return localGateway.getLanguageCodeFlow()
    }
}
