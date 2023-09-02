package org.thechance.api_gateway.data.localizedMessages

import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.localizedMessages.languages.*

@Single
class LocalizedMessagesFactory {
    fun createLocalizedMessages(languageCode: String): LocalizedMessages {
        return map[languageCode.uppercase()] ?: EnglishLocalizedMessages()
    }
}

private val map = mapOf(
    Language.ENGLISH.code to EnglishLocalizedMessages(),
    Language.ARABIC.code to ArabicLocalizedMessages(),
    Language.EGYPT.code to EgyptianArabicLocalizedMessages(),
    Language.PALESTINE.code to PalestinianArabicLocalizedMessages(),
    Language.SYRIA.code to SyrianArabicLocalizedMessages(),
    Language.IRAQ.code to IraqiArabicLocalizedMessages()
)


enum class Language(val code: String) {
    ENGLISH("EN"),
    ARABIC("AR"),
    EGYPT("EG"),
    PALESTINE("PS"),
    SYRIA("SY"),
    IRAQ("IQ"),
}