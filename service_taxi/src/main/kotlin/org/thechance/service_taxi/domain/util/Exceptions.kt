package org.thechance.service_taxi.domain.util

open class TaxiException : Throwable()
object InvalidIdException : TaxiException()
object ResourceNotFoundException : TaxiException()
object MissingParameterException : TaxiException()
object AlreadyExistException : TaxiException()
object CantBeNullException : TaxiException()
class MultiErrorException(val errorCodes: List<Int>) : TaxiException()


const val INVALID_ID = 3001
const val INVALID_PLATE = 3002
const val INVALID_COLOR = 3003
const val INVALID_CAR_TYPE = 3004
const val SEAT_OUT_OF_RANGE = 3005
const val INVALID_LOCATION = 3006
const val INVALID_RATE = 3007
const val INVALID_DATE = 3008
const val INVALID_PRICE = 3009
const val ALREADY_EXIST = 3010
const val INVALID_REQUEST_PARAMETER = 3100
const val REQUIRED_QUERY = 3200
const val NOT_FOUND = 3404