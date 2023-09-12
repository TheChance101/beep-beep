package di

import org.koin.dsl.module
import resources.strings.ar.Arabic
import resources.strings.en.English
import util.userCountryCode
import util.userLanguage

val resourcesModule = module {
    single {
        val language = userLanguage
        val countryCode = userCountryCode
        when (language) {
            "ar" -> Arabic()
            else -> English()
        }
    }
}

