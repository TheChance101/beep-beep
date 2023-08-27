package org.thechance.api_gateway.data.localizedMessages

import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.localizedMessages.languages.ArabicLocalizedMessages
import org.thechance.api_gateway.data.localizedMessages.languages.EgyptLocalizedMessages
import org.thechance.api_gateway.data.localizedMessages.languages.EnglishLocalizedMessages
import org.thechance.api_gateway.data.localizedMessages.languages.LocalizedMessages
import org.thechance.api_gateway.data.localizedMessages.languages.PalestineLocalizedMessages
import java.util.*

@Single
class LocalizedMessagesFactory {
    fun createLocalizedMessages(locale: Locale): LocalizedMessages {
        return when (locale.language) {
            Language.ENGLISH.code -> EnglishLocalizedMessages()
            Language.ARABIC.code -> {
                when (locale.country) {
                    Country.EGYPT.code -> EgyptLocalizedMessages()
                    Country.PALESTINE.code -> PalestineLocalizedMessages()
                    else -> ArabicLocalizedMessages()
                }
            }
            else -> EnglishLocalizedMessages()
        }
    }
}


enum class Language(val code: String) {
    ENGLISH("en"),
    ARABIC("ar")
}

enum class Country(val code: String) {
    EGYPT("EG"),
    PALESTINE("PS")
}