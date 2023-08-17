package org.thechance.api_gateway.domain.usecase.util

import org.thechance.api_gateway.domain.entity.MultiErrorException
import org.thechance.api_gateway.domain.entity.MultiLocalizedMessageException

suspend fun tryToExecute(
    getLocalizedErrors: suspend (errorCode: List<Int>) -> List<String>,
    function: suspend () -> Boolean,
): Boolean {
    try {
        return function()
    } catch (e: MultiErrorException) {
        throw MultiLocalizedMessageException(getLocalizedErrors(e.errors))
    }
}