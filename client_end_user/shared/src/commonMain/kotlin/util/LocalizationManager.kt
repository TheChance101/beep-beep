package util

import androidx.compose.ui.unit.LayoutDirection
import resources.strings.IStringResources
import resources.strings.ar.Arabic
import resources.strings.ar.ArabicEG
import resources.strings.ar.ArabicIQ
import resources.strings.ar.ArabicPS
import resources.strings.ar.ArabicSY
import resources.strings.en.English


object LocalizationManager {

    fun getStringResources(languageCode: String): IStringResources {
        return when (languageCode.ifEmpty { "en" }) {
            "ar" -> Arabic()
            "ps" -> ArabicPS()
            "iq" -> ArabicIQ()
            "sy" -> ArabicSY()
            "eg" -> ArabicEG()
            else -> English()
        }
    }

    fun getLayoutDirection(languageCode: String): LayoutDirection {
        return when (languageCode.ifEmpty { "en" }) {
            "en" -> LayoutDirection.Ltr
            else -> LayoutDirection.Rtl
        }
    }
}