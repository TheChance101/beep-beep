package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.ThemeMode
import org.thechance.common.domain.getway.IIdentityGateway

interface IThemeManagementUseCase {

    suspend fun switchTheme(mode: ThemeMode)

    suspend fun getThemeMode(): ThemeMode

}

class ThemeManagementUseCase(private val identityGateway: IIdentityGateway) : IThemeManagementUseCase {

    override suspend fun switchTheme(mode: ThemeMode) {
        identityGateway.updateThemeMode(mode)
    }

    override suspend fun getThemeMode() = identityGateway.getThemeMode()

}