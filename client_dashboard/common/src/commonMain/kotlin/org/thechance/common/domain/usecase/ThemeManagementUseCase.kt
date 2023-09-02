package org.thechance.common.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.thechance.common.domain.getway.IIdentityGateway

interface IThemeManagementUseCase {

    suspend fun switchTheme(mode: Boolean)

    suspend fun getThemeMode(): Flow<Boolean>

}

class ThemeManagementUseCase(private val identityGateway: IIdentityGateway) : IThemeManagementUseCase {

    override suspend fun switchTheme(mode: Boolean) {
        identityGateway.updateThemeMode(mode)
    }

    override suspend fun getThemeMode() = identityGateway.getThemeMode()

}