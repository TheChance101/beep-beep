package org.thechance.service_notification.endpoints.utils

import io.ktor.http.*
import org.thechance.service_notification.domain.entity.MissingRequestParameterException
import org.thechance.service_notification.endpoints.MISSING_PARAMETER

fun Parameters.extractString(key: String): String? {
    return this[key]?.trim()?.takeIf { it.isNotEmpty() }
}

fun Parameters.extractInt(key: String): Int? {
    return this[key]?.toIntOrNull()
}

fun Parameters.requireNotEmpty(parameterName: String): String {
    return this[parameterName]
        .takeIf { it?.isNotEmpty() == true }
        ?: throw MissingRequestParameterException(MISSING_PARAMETER)
}