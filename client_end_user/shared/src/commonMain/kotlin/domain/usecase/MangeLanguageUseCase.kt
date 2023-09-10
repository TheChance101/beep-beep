package domain.usecase

import domain.gateway.local.ILocalConfigurationGateway

interface IMangeLanguageUseCase {
    suspend fun saveLanguageCode(code: String): Boolean
    suspend fun getLanguageCode(): String
}

class MangeLanguageUseCase(
    private val localGateway: ILocalConfigurationGateway,
) : IMangeLanguageUseCase {
    override suspend fun saveLanguageCode(code: String): Boolean {
        localGateway.saveLanguageCode(code)
        return true
    }

    override suspend fun getLanguageCode(): String {
        return localGateway.getLanguageCode()
    }
}