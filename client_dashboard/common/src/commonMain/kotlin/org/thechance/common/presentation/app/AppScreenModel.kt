package org.thechance.common.presentation.app

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.thechance.common.domain.usecase.IManageThemeUseCase

class AppScreenModel(
    private val themeManagement: IManageThemeUseCase
) : StateScreenModel<Boolean>(false) {

    init {
        getThemeMode()
    }

    private fun getThemeMode() {
        coroutineScope.launch(Dispatchers.IO) {
            themeManagement.getThemeMode().collect { isDarkTheme ->
                mutableState.update { isDarkTheme }
            }
        }
    }

}