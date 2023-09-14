package org.thechance.service_restaurant.domain.utils

import org.thechance.service_restaurant.domain.entity.Location

fun getCurrencyForLocation(location: Location): String {
    val countryMappings = mapOf(
        Country.EGYPT to Pair(22.0..31.0, 25.0..34.0),
        Country.IRAQ to Pair(29.0..38.0, 38.0..48.5),
        Country.SYRIA to Pair(32.0..37.5, 35.5..42.0),
        Country.PALESTINE to Pair(31.0..33.5, 34.0..36.5)
    )
    countryMappings.forEach { item ->
        val latRange = item.value.first
        val lonRange = item.value.second
        if (location.latitude in latRange && location.longitude in lonRange) {
            return getCurrencyForCountry(item.key)
        }
    }
    return Country.UNITED_STATES.currency
}

private fun getCurrencyForCountry(countryName: Country): String {
    return when (countryName) {
        Country.EGYPT -> Country.EGYPT.currency
        Country.IRAQ -> Country.IRAQ.currency
        Country.SYRIA -> Country.SYRIA.currency
        Country.PALESTINE -> Country.PALESTINE.currency
        Country.UNITED_STATES -> Country.UNITED_STATES.currency
    }
}