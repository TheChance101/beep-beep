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

    fun getStringResources(languageCode: LanguageCode): IStringResources {
        return when (languageCode) {
            LanguageCode.EN -> English()
            LanguageCode.AR -> Arabic()
            LanguageCode.IQ -> ArabicIQ()
            LanguageCode.SY -> ArabicSY()
            LanguageCode.EG -> ArabicEG()
            LanguageCode.PS -> ArabicPS()
        }
    }

    fun getLayoutDirection(languageCode: LanguageCode): LayoutDirection {
        return when (languageCode) {
            LanguageCode.EN -> LayoutDirection.Ltr
            else -> LayoutDirection.Rtl
        }
    }
}
