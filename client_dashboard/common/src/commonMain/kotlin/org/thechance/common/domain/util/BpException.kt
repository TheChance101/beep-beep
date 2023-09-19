package org.thechance.common.domain.util

open class BeepBeepException(message: String) : Exception(message)

class UnknownErrorException(message: String) : BeepBeepException(message)

class NoInternetException : BeepBeepException("No internet connection")

class NotFoundException(message: String) : BeepBeepException(message)

//region user Exceptions
open class IdentityException(message: String) : BeepBeepException(message)

class InvalidPasswordException(message: String) : IdentityException(message)

class InvalidUserNameException(message: String) : IdentityException(message)

class UsernameCannotBeBlankException(message: String) : IdentityException(message)

class PasswordCannotBeBlankException(message: String) : IdentityException(message)
class InvalidUserRequestParameterException(message: String) : IdentityException(message)

//endregion


//region taxi Exceptions
open class TaxiException(message: String) : BeepBeepException(message)

class InvalidTaxiIdException(message: String) : TaxiException(message)

class InvalidTaxiPlateException(message: String) : TaxiException(message)

class InvalidTaxiColorException(message: String) : TaxiException(message)

class InvalidCarTypeException(message: String) : TaxiException(message)

class SeatOutOfRangeException(message: String) : TaxiException(message)


class TaxiAlreadyExistsException(message: String) : TaxiException(message)

class InvalidTaxiRequestParameterException(message: String) : TaxiException(message)
class TaxiNotFoundException(message: String) : TaxiException(message)

//endregion


//region restaurant Exceptions
open class RestaurantException(message: String) : BeepBeepException(message)
class RestaurantInvalidIdException(message: String) : RestaurantException(message)

class RestaurantInvalidNameException(message: String) : RestaurantException(message)

class RestaurantInvalidLocationException(message: String) : RestaurantException(message)

class RestaurantInvalidDescriptionException(message: String) : RestaurantException(message)

class RestaurantInvalidPhoneException(message: String) : RestaurantException(message)

class RestaurantInvalidTimeException(message: String) : RestaurantException(message)

class RestaurantInvalidPageException(message: String) : RestaurantException(message)

class RestaurantInvalidPageLimitException(message: String) : RestaurantException(message)


class RestaurantInvalidUpdateParameterException(message: String) : RestaurantException(message)

class RestaurantInvalidAddressException(message: String) : RestaurantException(message)

class RestaurantInvalidRequestParameterException(message: String) : RestaurantException(message)

class RestaurantNotFoundException(message: String) : RestaurantException(message)

class RestaurantErrorAddException(message: String) : RestaurantException(message)

class RestaurantClosedException(message: String) : RestaurantException(message)

class CuisineNameAlreadyExistedException(message: String) : RestaurantException(message)

//endregion
