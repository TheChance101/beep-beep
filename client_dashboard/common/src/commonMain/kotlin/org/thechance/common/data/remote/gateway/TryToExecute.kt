package org.thechance.common.data.remote.gateway


internal fun Map<String, String>.containsErrors(vararg errorCodes: String): Boolean =
    keys.containsAll(errorCodes.toList())

internal fun Map<String, String>.getOrEmpty(key: String): String = get(key) ?: ""
