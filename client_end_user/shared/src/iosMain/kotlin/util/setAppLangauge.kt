package util

import platform.Foundation.NSBundle
import platform.Foundation.NSUserDefaults
import platform.Foundation.setValue


actual fun setAppLanguage(language: String) {
    setLocale(listOf(language))
    localize("AppleLanguages")
}

fun setLocale(languageCode: List<String>) {
    NSUserDefaults.standardUserDefaults.setValue(languageCode, forKey = "AppleLanguages")
    NSUserDefaults.standardUserDefaults.synchronize()
}

fun localize(key: String): String {
    val path = NSBundle.mainBundle.pathForResource(
        "resources",
        ofType = "strings",
        inDirectory = null,
        forLocalization = null
    )
    val bundle = path?.let { NSBundle.bundleWithPath(it) }
    return bundle?.localizedStringForKey(key, value = null, table = null) ?: "ar"
}

