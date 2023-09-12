package util

import platform.Foundation.NSLocale
import platform.Foundation.countryCode
import platform.Foundation.currentLocale
import platform.Foundation.languageCode

actual val userLanguage: String?
    get() = NSLocale.currentLocale.languageCode

actual val userCountryCode: String?
    get() = NSLocale.currentLocale.countryCode
