package org.thechance.common.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.thechance.common.domain.getway.IUserLocalGateway

interface IManageThemeUseCase {

    suspend fun switchTheme(mode: Boolean)

    suspend fun getThemeMode(): Flow<Boolean>

}

class ManageThemeUseCase(private val userLocalGateway: IUserLocalGateway) : IManageThemeUseCase {

    override suspend fun switchTheme(mode: Boolean) {
        userLocalGateway.updateThemeMode(mode)
    }

    override suspend fun getThemeMode() = userLocalGateway.getThemeMode()

}