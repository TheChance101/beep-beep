package domain.usecase

import domain.gateway.local.ILocalConfigurationGateway

interface IMangeLanguageUseCase {
    suspend fun saveLanguageCode(code: String)
    suspend fun getLanguageCode(): String
}

class MangeLanguageUseCase(
    private val localGateway: ILocalConfigurationGateway,
) : IMangeLanguageUseCase {
    override suspend fun saveLanguageCode(code: String) {
       return localGateway.saveLanguageCode(code)
    }

    override suspend fun getLanguageCode(): String {
        return localGateway.getLanguageCode()
    }
}