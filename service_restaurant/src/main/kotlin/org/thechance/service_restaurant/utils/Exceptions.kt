package org.thechance.service_restaurant.utils

import kotlin.reflect.jvm.internal.impl.util.ModuleVisibilityHelper.EMPTY

open class RestaurantException(message: String) : Throwable(message)

class ResourceNotFoundException(code: Int) : RestaurantException(code.toString())

class MissingParameterException(code: Int) : RestaurantException(code.toString())

class InvalidParameterException(code: Int) : RestaurantException(code.toString())

class MultiErrorException(errorCodes: List<Int>) : RestaurantException(errorCodes.joinToString(",") { it.toString() })


const val INVALID_ID = 2001
const val INVALID_NAME = 2002
const val INVALID_LOCATION = 2003
const val INVALID_DESCRIPTION = 2004
const val INVALID_PRICE_LEVEL = 2005
const val INVALID_RATE = 2006
const val INVALID_PHONE = 2007
const val INVALID_TIME = 2008
const val INVALID_REQUEST_PARAMETER = 2100
const val NOT_FOUND = 2404