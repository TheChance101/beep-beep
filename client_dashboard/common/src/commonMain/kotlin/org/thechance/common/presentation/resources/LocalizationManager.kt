package org.thechance.common.presentation.resources

import androidx.compose.ui.unit.LayoutDirection


object LocalizationManager {

    fun getStringResources(languageCode: String): StringResources {
        return when (languageCode) {
            "ar" -> Arabic
            "iq" -> Iraq
            "ps" -> Palestine
            "sy" -> Syria
            "eg" -> Egypt
            else -> English
        }
    }

    fun getLayoutDirection(languageCode: String): LayoutDirection {
        return when (languageCode) {
            "us" -> LayoutDirection.Ltr
            else -> LayoutDirection.Rtl
        }
    }
}