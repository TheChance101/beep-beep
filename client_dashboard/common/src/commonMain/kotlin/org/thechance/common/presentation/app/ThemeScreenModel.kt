package org.thechance.common.presentation.app

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.thechance.common.domain.entity.ThemeMode
import org.thechance.common.domain.usecase.IThemeManagementUseCase

class ThemeScreenModel(
    private val themeManagement: IThemeManagementUseCase
) : StateScreenModel<Boolean>(false), SwitchThemeInteractionListener {

    init {
        getThemeMode()
    }

    override fun onSwitchTheme() {
        coroutineScope.launch(Dispatchers.IO) {
            val mode = if (state.value) ThemeMode.LIGHT else ThemeMode.DARK
            themeManagement.switchTheme(mode)
            getThemeMode()
        }
    }

    private fun getThemeMode() {
        coroutineScope.launch(Dispatchers.IO) {
            val isDarkMode = themeManagement.getThemeMode() == ThemeMode.DARK
            mutableState.update { isDarkMode }
        }
    }

}