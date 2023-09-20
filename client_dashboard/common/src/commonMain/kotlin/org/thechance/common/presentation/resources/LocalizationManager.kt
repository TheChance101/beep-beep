package org.thechance.common.presentation.resources

import androidx.compose.ui.unit.LayoutDirection


object LocalizationManager {

    fun getStringResources(languageCode: String): StringResources {
        return when (languageCode) {
            "ar" -> arabic
            else -> english
        }
    }

    fun getLayoutDirection(languageCode: String): LayoutDirection {
        return when (languageCode) {
            "en" -> LayoutDirection.Ltr
            else -> LayoutDirection.Rtl
        }
    }
}