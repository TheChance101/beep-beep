package org.thechance.service_location.data.utils

class MultiErrorException(val errorCodes: List<Int>) : Throwable(errorCodes.toString())

const val INVALID_LOCATION = 2003
