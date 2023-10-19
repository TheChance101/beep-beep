package org.thechance.service_chat.domain.utils

class MultiErrorException(val errorCodes: List<Int>) : Throwable(errorCodes.toString())


const val TICKET_NOT_FOUND = 6000
const val SUPPORT_AGENT_NOT_FOUND = 6001
