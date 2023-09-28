package org.thechance.service_identity.domain.util


val countryMap = mapOf(
    "Egypt" to "+20",
    "Iraq" to "+964",
    "Palestine" to "+970",
    "Syria" to "+963",
    "USA" to "+1"
)

enum class CountryCurrency(val currency: String) {
    Egypt("EGP"),
    Iraq("IQD"),
    Palestine("ILS"),
    USA("USD"),
    Syria("SYP")
}