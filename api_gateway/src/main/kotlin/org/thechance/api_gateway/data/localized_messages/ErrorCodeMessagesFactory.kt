package org.thechance.api_gateway.data.localized_messages

import org.thechance.api_gateway.data.localized_messages.error_codes.*
import java.util.*

object ErrorCodeMessagesFactory {
    fun createErrorCodeMessages(locale: Locale): LocalizedMessages {
        return when (locale.language) {
            "en" -> EnglishLocalizedMessages()
            "ar" -> {
                when (locale.country) {
                    "EG" -> EgyptLocalizedMessages()
                    "PS" -> PalestineLocalizedMessages()
                    else -> ArabicLocalizedMessages()
                }
            }

            else -> EnglishLocalizedMessages()
        }
    }
}
