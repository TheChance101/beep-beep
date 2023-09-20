package org.thechance.common.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.thechance.common.domain.getway.IUserLocalGateway

interface IThemeManagementUseCase {

    suspend fun switchTheme(mode: Boolean)

    suspend fun getThemeMode(): Flow<Boolean>

}

class ThemeManagementUseCase(private val userLocalGateway: IUserLocalGateway) : IThemeManagementUseCase {

    override suspend fun switchTheme(mode: Boolean) {
        userLocalGateway.updateThemeMode(mode)
    }

    override suspend fun getThemeMode() = userLocalGateway.getThemeMode()

}