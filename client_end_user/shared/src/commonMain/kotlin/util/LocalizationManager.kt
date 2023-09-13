package util

import resources.strings.IStringResources
import resources.strings.ar.Arabic
import resources.strings.ar.ArabicEG
import resources.strings.ar.ArabicIQ
import resources.strings.ar.ArabicPS
import resources.strings.ar.ArabicSY
import resources.strings.en.English


object LocalizationManager {

    fun setLocale(languageCode: String): IStringResources {
        return when (languageCode.ifEmpty { "en" }) {
            "ar" -> Arabic()
            "ps" -> ArabicPS()
            "iq" -> ArabicIQ()
            "sy" -> ArabicSY()
            "eg" -> ArabicEG()
            else -> English()
        }
    }
}