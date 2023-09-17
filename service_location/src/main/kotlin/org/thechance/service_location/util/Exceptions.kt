package org.thechance.service_location.util

open class LocationServiceException(message: String) : Throwable(message)

class MissingParameterException(message: String) : LocationServiceException(message)

class InvalidLocationException(message: String) : LocationServiceException(message)
class ConnectionErrorException(message: String) : LocationServiceException(message)

const val INVALID_REQUEST_PARAMETER = "4000"
const val INVALID_LOCATION = "4001"
const val CONNECTION_ERROR = "4002"