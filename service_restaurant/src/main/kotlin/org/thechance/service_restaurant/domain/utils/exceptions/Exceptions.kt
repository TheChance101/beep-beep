package org.thechance.service_restaurant.domain.utils.exceptions

class MultiErrorException(val errorCodes: List<Int>) : Throwable(errorCodes.toString())

const val INVALID_ID = 2001
const val INVALID_NAME = 2002
const val INVALID_LOCATION = 2003
const val INVALID_DESCRIPTION = 2004
const val INVALID_PRICE_LEVEL = 2005
const val INVALID_RATE = 2006
const val INVALID_PHONE = 2007
const val INVALID_TIME = 2008

const val INVALID_PAGE = 2009
const val INVALID_PAGE_LIMIT = 2010


const val INVALID_ONE_OR_MORE_IDS = 2011
const val INVALID_PERMISSION_UPDATE_LOCATION = 2012
const val INVALID_UPDATE_PARAMETER = 2013
const val INVALID_PROPERTY_RIGHTS = 2014
const val INVALID_PRICE = 2015
const val INVALID_CUISINE_LIMIT = 2016

const val INVALID_REQUEST_PARAMETER = 2100
const val NOT_FOUND = 2404
