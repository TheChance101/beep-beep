package org.thechance.service_restaurant.domain.validation


import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.function.Executable
import org.thechance.service_restaurant.domain.entity.Location
import org.thechance.service_restaurant.domain.entity.Restaurant
import org.thechance.service_restaurant.domain.usecase.validation.RestaurantValidationUseCase
import org.thechance.service_restaurant.domain.utils.Validation
import org.thechance.service_restaurant.domain.utils.Validation.Companion.DESCRIPTION_MIN_LENGTH
import org.thechance.service_restaurant.domain.utils.Validation.Companion.NULL_DOUBLE
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_DESCRIPTION
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_ID
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_LOCATION
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_NAME
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_PERMISSION_UPDATE_LOCATION
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_PHONE
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_PRICE_LEVEL
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_PROPERTY_RIGHTS
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_RATE
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_TIME
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException

class RestaurantValidationTest  {

    private val restaurantValidation = RestaurantValidationUseCase(Validation())



    //region create validationRestaurant
    @Test
    fun `should pass validation when create restaurant with just require fields that valid`() {
        // given a valid fields of restaurant that  require
        val restaurantExecutable = Executable {
            restaurantValidation.validateAddRestaurant(fakeRestaurant()[0])
        }
        //then no exception should be thrown and pass validation
        assertDoesNotThrow(restaurantExecutable)
    }

    @Test
    fun `should pass validation when create restaurant with all fields that valid`() {
        // given a valid fields of restaurant with optional fields
        val restaurantExecutable = Executable {
            restaurantValidation.validateAddRestaurant(fakeRestaurant()[2])
        }
        //then no exception should be thrown and pass validation
        assertDoesNotThrow(restaurantExecutable)
    }

    @Test
    fun `should pass validation when create restaurant with blank price level`() {
        // given a restaurant when price level is blank
        val blankPrice = fakeRestaurant().first().copy(priceLevel = "")
        // then no exception should be thrown
        restaurantValidation.validateAddRestaurant(blankPrice)
    }

    @Test
    fun `should pass validation when create restaurant with valid price level`() {
        // given a restaurant when price level is blank
        val blankPrice = fakeRestaurant().first().copy(priceLevel = "$")
        // then no exception should be thrown
        restaurantValidation.validateAddRestaurant(blankPrice)
    }

    @Test
    fun `should throw exception when creat restaurant with name is empty`() {
        // given a restaurant when name is empty
        val emptyNameExecutable = Executable {
            restaurantValidation.validateAddRestaurant(fakeRestaurant()[0].copy(name = " "))
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, emptyNameExecutable)
        assert(error.errorCodes.contains(INVALID_NAME))
    }

    @Test
    fun `should throw exception when creat restaurant with name is long`() {
        // given a restaurant when name is more than 25
        val longNameExecutable = Executable {
            restaurantValidation.validateAddRestaurant(fakeRestaurant()[0].copy(name = "A".repeat(51)))
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, longNameExecutable)
        assert(error.errorCodes.contains(INVALID_NAME))
    }

    @Test
    fun `should throw exception when creat restaurant with name is short`() {
        // given a restaurant when name is less than four letter
        val shortNameExecutable = Executable {
            restaurantValidation.validateAddRestaurant(fakeRestaurant()[0].copy(name = "Asi"))
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, shortNameExecutable)
        assert(error.errorCodes.contains(INVALID_NAME))
    }

    @Test
    fun `should throw exception when creat restaurant with description is invalid `() {
        // given a restaurant when description is more than 255
        val longDescriptionExecutable = Executable {
            restaurantValidation.validateAddRestaurant(fakeRestaurant()[0].copy(description = "A".repeat(256)))
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, longDescriptionExecutable)
        assert(error.errorCodes.contains(INVALID_DESCRIPTION))
    }

    @Test
    fun `should pass when creat restaurant with description is valid `() {
        // given a restaurant when description is 255
        val longDescriptionExecutable = Executable {
            restaurantValidation.validateAddRestaurant(fakeRestaurant()[0].copy(description = "A".repeat(255)))
        }
        // then check if throw exception
        assertDoesNotThrow(longDescriptionExecutable)
    }

    @Test
    fun `should throw exception when create restaurant with rate is more than five`() {
        // given a restaurant when rate is above upper bound  5.0
        val upperRateExecutable = Executable {
            restaurantValidation.validateAddRestaurant(fakeRestaurant()[0].copy(rate = 5.1))
        }

        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, upperRateExecutable)
        assert(error.errorCodes.contains(INVALID_RATE))

    }

    @Test
    fun `should throw exception when create restaurant with phone is invalid`() {
        // given a restaurant when phone is character
        val characterPhoneExecutable = Executable {
            restaurantValidation.validateAddRestaurant(fakeRestaurant()[0].copy(phone = "s".repeat(10)))
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, characterPhoneExecutable)
        assert(error.errorCodes.contains(INVALID_PHONE))
    }

    @Test
    fun `should pass when create restaurant with phone is valid`() {
        // given a restaurant when phone is character
        val characterPhoneExecutable = Executable {
            restaurantValidation.validateAddRestaurant(fakeRestaurant()[0].copy(phone = "1".repeat(10)))
        }
        // then check if throw exception
        assertDoesNotThrow(characterPhoneExecutable)
    }

    @Test
    fun `should throw exception when create restaurant with phone is short`() {
        // given a restaurant when phone is less than 10
        val shortPhoneExecutable = Executable {
            restaurantValidation.validateAddRestaurant(fakeRestaurant()[0].copy(phone = "1".repeat(9)))
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, shortPhoneExecutable)
        assert(error.errorCodes.contains(INVALID_PHONE))
    }

    @Test
    fun `should throw exception when create restaurant with phone is long`() {
        // given a restaurant when phone is more than 10
        val longPhoneExecutable = Executable {
            restaurantValidation.validateAddRestaurant(fakeRestaurant()[0].copy(phone = "1".repeat(11)))
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, longPhoneExecutable)
        assert(error.errorCodes.contains(INVALID_PHONE))
    }

    @Test
    fun `should throw exception when create restaurant with phone is empty`() {
        // given a restaurant when phone is empty
        val emptyPhoneExecutable = Executable {
            restaurantValidation.validateAddRestaurant(fakeRestaurant()[0].copy(phone = " "))
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, emptyPhoneExecutable)
        assert(error.errorCodes.contains(INVALID_PHONE))
    }

    @Test
    fun `should throw exception when create restaurant with latitude invalid`() {
        // given a restaurant when latitude is more than 90.0
        val invalidLatitudeExecutable = Executable {
            restaurantValidation.validateAddRestaurant(
                fakeRestaurant()[0].copy(
                    address = Location(
                        latitude = 95.0,
                        longitude = 200.0
                    )
                )
            )
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, invalidLatitudeExecutable)
        assert(error.errorCodes.contains(INVALID_LOCATION))
    }

    @Test
    fun `should throw exception when create restaurant with longitude invalid`() {
        // given a restaurant when longitude is more than 180.0
        val invalidLongitudeExecutable = Executable {
            restaurantValidation.validateAddRestaurant(fakeRestaurant()[0].copy(address = Location(40.0, -190.0)))
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, invalidLongitudeExecutable)
        assert(error.errorCodes.contains(INVALID_LOCATION))
    }

    @Test
    fun `should throw exception when create restaurant with latitude and longitude invalid`() {
        val invalidLongitudeAndLatitudeExecutable = Executable {
            restaurantValidation.validateAddRestaurant(fakeRestaurant()[0].copy(address = Location(-91.0, -181.0)))
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, invalidLongitudeAndLatitudeExecutable)
        assert(error.errorCodes.contains(INVALID_LOCATION))
    }


    @Test
    fun `should throw exception when create restaurant with invalid price level`() {
        // given a restaurant when price level not is $, $$, $$$, $$$$
        val invalidPriceLevelExecutable = Executable {
            restaurantValidation.validateAddRestaurant(fakeRestaurant()[0].copy(priceLevel = "invalid_level"))
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, invalidPriceLevelExecutable)
        assert(error.errorCodes.contains(INVALID_PRICE_LEVEL))
    }

    //endregion

    //region update restaurant details

    @Test
    fun `should throw exception when update restaurant with address that not null`() {
        // given a restaurant when address is not null
        val invalidAddressExecutable = Executable {
            restaurantValidation.validateUpdateRestaurantDetails(
                fakeRestaurant()[2].copy(
                    address = Location(
                        40.0,
                        40.0
                    )
                )
            )
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, invalidAddressExecutable)
        assert(error.errorCodes.contains(INVALID_PERMISSION_UPDATE_LOCATION))
    }

    @Test
    fun `should pass when update restaurant with address that null`() {
        // given a restaurant when address is null
        val invalidAddressExecutable = Executable {
            restaurantValidation.validateUpdateRestaurantDetails(
                fakeRestaurant()[2].copy(
                    address = Location(
                        NULL_DOUBLE,
                        NULL_DOUBLE
                    )
                )
            )
        }
        // then check if throw exception
        assertDoesNotThrow(invalidAddressExecutable)
    }

    @Test
    fun `should throw exception when update restaurant with invalid id and owner id`() {
        // given a restaurant when id and owner id is invalid
        val invalidIdExecutable = Executable {
            restaurantValidation.validateUpdateRestaurantDetails(
                fakeRestaurant()[0].copy(
                    id = "invalid_id",
                    ownerId = "invalid_id"
                )
            )
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, invalidIdExecutable)
        assert(error.errorCodes.contains(INVALID_ID))
    }

    @Test
    fun `should throw exception when update restaurant with invalid price level`() {
        // given a restaurant when price level not is $, $$, $$$, $$$$
        val invalidPriceLevelExecutable = Executable {
            restaurantValidation.validateUpdateRestaurantDetails(fakeRestaurant()[0].copy(priceLevel = "invalid_level"))
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, invalidPriceLevelExecutable)
        assert(error.errorCodes.contains(INVALID_PRICE_LEVEL))
    }

    @Test
    fun `should throw exception when update restaurant with name is empty`() {
        // given a restaurant when name is empty
        val emptyNameExecutable = Executable {
            restaurantValidation.validateUpdateRestaurantDetails(fakeRestaurant()[0].copy(name = " "))
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, emptyNameExecutable)
        assert(error.errorCodes.contains(INVALID_NAME))
    }

    @Test
    fun `should throw exception when update restaurant with name is long`() {
        // given a restaurant when name is more than 25
        val longNameExecutable = Executable {
            restaurantValidation.validateUpdateRestaurantDetails(fakeRestaurant()[0].copy(name = "A".repeat(51)))
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, longNameExecutable)
        assert(error.errorCodes.contains(INVALID_NAME))
    }

    @Test
    fun `should throw exception when update restaurant with name is short`() {
        // given a restaurant when name is less than four letter
        val shortNameExecutable = Executable {
            restaurantValidation.validateUpdateRestaurantDetails(fakeRestaurant()[0].copy(name = "Asi"))
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, shortNameExecutable)
        assert(error.errorCodes.contains(INVALID_NAME))
    }

    @Test
    fun `should throw exception when update restaurant with description is invalid `() {
        // given a restaurant when description is more than 255
        val longDescriptionExecutable = Executable {
            restaurantValidation.validateUpdateRestaurantDetails(fakeRestaurant()[0].copy(description = "A".repeat(256)))
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, longDescriptionExecutable)
        assert(error.errorCodes.contains(INVALID_DESCRIPTION))
    }

    @Test
    fun `should pass when update restaurant with description is valid `() {
        // given a restaurant when description is 255
        val longDescriptionExecutable = Executable {
            restaurantValidation.validateUpdateRestaurantDetails(fakeRestaurant()[1].copy(description = "A".repeat(255)))
        }
        // then check if throw exception
        assertDoesNotThrow(longDescriptionExecutable)
    }

    @Test
    fun `should pass when update restaurant with description is empty`() {
        // given a restaurant when description is 255
        val longDescriptionExecutable = Executable {
            restaurantValidation.validateUpdateRestaurantDetails(fakeRestaurant()[1].copy(description = ""))
        }
        // then check if throw exception
        assertDoesNotThrow(longDescriptionExecutable)
    }

    @Test
    fun `should throw exception for invalid price level`() {
        // given a restaurant when price level not is $, $$, $$$, $$$$
        val invalidPriceLevelUpdate = fakeRestaurant()[2].copy(priceLevel = "invalid_level")
        val upperRestaurantExecutable =
            Executable { restaurantValidation.validateUpdateRestaurantDetails(invalidPriceLevelUpdate) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, upperRestaurantExecutable)
        assert(error.errorCodes.contains(INVALID_PRICE_LEVEL))
    }

    @Test
    fun `should throw exception for invalid phone`() {
        // given a restaurant when phone is invalid
        val invalidPhoneUpdate = fakeRestaurant()[2].copy(phone = "122")
        val upperRestaurantExecutable =
            Executable { restaurantValidation.validateUpdateRestaurantDetails(invalidPhoneUpdate) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, upperRestaurantExecutable)
        assert(error.errorCodes.contains(INVALID_PHONE))
    }

    @Test
    fun `should throw exception for invalid closing time`() {
        val invalidClosingTimeUpdate = fakeRestaurant()[2].copy(closingTime = "000:00")
        val upperRestaurantExecutable =
            Executable { restaurantValidation.validateUpdateRestaurantDetails(invalidClosingTimeUpdate) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, upperRestaurantExecutable)
        assert(error.errorCodes.contains(INVALID_TIME))
    }

    @Test
    fun `should throw exception for invalid opening time`() {
        // given a restaurant when opening time is invalid
        val invalidOpeningTimeUpdate = fakeRestaurant()[2].copy(openingTime = "07:6")
        val upperRestaurantExecutable =
            Executable { restaurantValidation.validateUpdateRestaurantDetails(invalidOpeningTimeUpdate) }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, upperRestaurantExecutable)
        assert(error.errorCodes.contains(INVALID_TIME))
    }

    @Test
    fun `should throw exception when update restaurant with rate is more than five`() {
        // given a restaurant when rate is above upper bound  5.0
        val upperRateExecutable = Executable {
            restaurantValidation.validateUpdateRestaurantDetails(fakeRestaurant()[0].copy(rate = 5.1))
        }
        // then check if throw exception
        val error = assertThrows(MultiErrorException::class.java, upperRateExecutable)
        assert(error.errorCodes.contains(INVALID_RATE))
    }

    //endregion

    //region update restaurant
    @Test
    fun `should pass when admin update restaurant with address that not null`() {
        // given a restaurant when address is not null
        val d = restaurantValidation.validateUpdateRestaurant(fakeRestaurant()[2].copy(address = Location(40.0, 40.0)))

        val invalidAddressExecutable = Executable {
            restaurantValidation.validateUpdateRestaurant(fakeRestaurant()[2].copy(address = Location(40.0, 40.0)))
        }
        // then check if pass
        println("dddddddd:$d")
        //assertDoesNotThrow(invalidAddressExecutable)
    }

    //endregion

    //region owner validation
    @Test
    fun `should throw exception when restaurant not exists in database`() {
        // given a restaurant with a valid ownerId
        val ownerId = "64cc5fdd52c4136b92938f8c"
        //when there is no restaurant with that ownerId
        val invalidOwnerIdExecutable = Executable {
            restaurantValidation.validateRestaurantOwnership(null, ownerId)
        }
        // then throw exception
        val error = assertThrows(MultiErrorException::class.java, invalidOwnerIdExecutable)
        assert(error.errorCodes.contains(INVALID_ID))
    }

    @Test
    fun `should pass validation when restaurant has valid ownership`() {
        // given a restaurant with a valid ownerId
        val validRestaurant = fakeRestaurant()[1]
        val ownerId = "3edf2fc8-6983-484f-a35c-8190f44a08c6"
        // when the restaurant has the same ownerId
        val restaurantExecutable = Executable {
            restaurantValidation.validateRestaurantOwnership(validRestaurant, ownerId)
        }
        //then no exception should be thrown and pass validation
        assertDoesNotThrow(restaurantExecutable)
    }

    @Test
    fun `should throw exception when restaurant has invalid ownership`() {
        // given a restaurant with a valid ownerId
        val validRestaurant = fakeRestaurant()[1]
        val ownerId = "64cc5fdd52c4131b92938f8c"
        // when the restaurant has not same ownerId
        val restaurantExecutable = Executable {
            restaurantValidation.validateRestaurantOwnership(validRestaurant, ownerId)
        }
        // then throw exception
        val error = assertThrows(MultiErrorException::class.java, restaurantExecutable)
        assert(error.errorCodes.contains(INVALID_PROPERTY_RIGHTS))
    }
    //endregion


    private fun fakeRestaurant(): List<Restaurant> {
        return listOf(
            Restaurant(
                name = "Good Restaurant",
                ownerId = "3edf2fc8-6983-484f-a35c-8190f44a08c6",
                address = Location(0.0, 0.0),
                phone = "1234561234",
                openingTime = "08:00",
                closingTime = "22:00",
                id = "",
            ),
            Restaurant(
                id = "3edf2fc8-6983-484f-a35c-8190f44a08c6",
                name = "Good Restaurant",
                ownerId = "3edf2fc8-6983-484f-a35c-8190f44a08c6",
                address = Location(NULL_DOUBLE, NULL_DOUBLE),
                phone = "1234561234",
                openingTime = "08:00",
                closingTime = "22:00",
                priceLevel = "$",
                rate = 4.5,
                description = "G".repeat(DESCRIPTION_MIN_LENGTH),
            ),
            Restaurant(
                id = "3edf2fc8-6983-484f-a35c-8190f44a08c6",
                name = "Good Restaurant",
                ownerId = "3edf2fc8-6983-484f-a35c-8190f44a08c5",
                address = Location(0.0, 0.0),
                phone = "1234561234",
                openingTime = "08:00",
                closingTime = "22:00",
                priceLevel = "$",
                rate = 4.5,
                description = "G".repeat(DESCRIPTION_MIN_LENGTH),
            )
        )
    }

}