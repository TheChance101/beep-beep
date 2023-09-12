package util

import java.util.Locale

actual val userLanguage: String?
    get() = Locale.getDefault().language

actual val userCountryCode: String?
    get() = Locale.getDefault().country