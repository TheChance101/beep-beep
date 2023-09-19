package org.thechance.service_identity.domain.util


import org.thechance.service_identity.domain.entity.Location


fun getCountryForLocation(location: Location?): String {
    val countryMappings = mapOf(
        "Egypt" to Pair(22.0..31.0, 25.0..34.0),
        "Iraq" to Pair(29.0..38.0, 38.0..48.5),
        "Syria" to Pair(32.0..37.5, 35.5..42.0),
        "Palestine" to Pair(31.0..33.5, 34.0..36.5)
    )
    if (location != null) {
        countryMappings.forEach { item ->
            val latRange = item.value.first
            val lonRange = item.value.second
            if (location.latitude in latRange && location.longitude in lonRange) {
                return item.key
            }
        }
    }
    return "US"
}