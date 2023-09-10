package org.thechance.service_identity.endpoints.util

import io.ktor.http.*

fun Parameters.extractInt(key: String): Int? {
    return this[key]?.toIntOrNull()
}
fun String?.toIntListOrNull(): List<Int>? {
    return this?.split(",")?.mapNotNull { it.toIntOrNull() }
}