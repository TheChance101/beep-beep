package org.thechance.api_gateway.data.localized_messages

import org.koin.core.annotation.Single
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