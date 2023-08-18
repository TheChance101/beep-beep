package org.thechance.service_restaurant.domain.validation

import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.function.Executable
import org.thechance.service_restaurant.domain.usecase.validation.OrderValidationUseCase
import org.thechance.service_restaurant.domain.utils.OrderStatus
import org.thechance.service_restaurant.domain.utils.Validation
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_ID
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_REQUEST_PARAMETER
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException

class OrderValidationTest {
    private val orderValidation = OrderValidationUseCase(Validation())

    @Test
    fun `should should throw MultiErrorException contains INVALID_ID when orderId is Invalid`() {
        // given
        val orderId = "not_good_id"
        val status = OrderStatus.PENDING
        //when
        val executable = Executable {
            orderValidation.validateUpdateOrder(orderId = orderId, status = status)
        }

        //then
        val error = assertThrows(MultiErrorException::class.java, executable)
        assertTrue( error.errorCodes.contains(INVALID_ID))
    }

    @Test
    fun `should should throw MultiErrorException contains INVALID_ID when orderId is Invalid in getOrderById`() {
       // given
        val orderId = "not_good_id"

        // when
        val executable = Executable { orderValidation.validateGetOrderById(orderId = orderId) }

        //then
        val error = assertThrows(MultiErrorException::class.java, executable)
        assertTrue(error.errorCodes.contains(INVALID_ID))
    }


    @Test
    fun `should should throw MultiErrorException contains INVALID_ID when restaurantId is Invalid`() {
       // given
        val restaurantId = "not_good_id"

        // when
        val executable = Executable {
            orderValidation.validateGetOrdersByRestaurantId(restaurantId = restaurantId)
        }

        //then
        val error = assertThrows(MultiErrorException::class.java, executable)
        assertTrue(error.errorCodes.contains(INVALID_ID))
    }

    @Test
    fun `should should throw MultiErrorException contains INVALID_ID when restaurantId is Invalid in order history`() {
       //given
        val restaurantId = "not_good_id"
        val page = 1
        val limit = 10

        //when
        val executable = Executable {
            orderValidation.validateGetOrdersHistory(
                restaurantId = restaurantId,
                page = page,
                limit = limit
            )
        }

        //then
        val error = assertThrows(MultiErrorException::class.java, executable)
        assertTrue(error.errorCodes.contains(INVALID_ID))
    }


    @Test
    fun `should pass when orderStatus is valid`() {
        //given
        val orderId = "3edf2fc8-6983-484f-a35c-8190f44a08c6"
        val status = OrderStatus.PENDING

        //when
        val executable = Executable {
            orderValidation.validateUpdateOrder(orderId = orderId, status = status)
        }

        //then
        assertDoesNotThrow(executable)
    }

    @Test
    fun `should throw MultiErrorException contains INVALID_REQUEST_PARAMETER when orderId is empty`() {
       //given
        val orderId = ""
        val status = OrderStatus.PENDING

        //when
        val executable = Executable {
            orderValidation.validateUpdateOrder(orderId = orderId, status = status)
        }

        //then
        val error = assertThrows(MultiErrorException::class.java, executable)
        assertTrue(error.errorCodes.contains(INVALID_REQUEST_PARAMETER))
    }

    @Test
    fun `should throw MultiErrorException contains INVALID_REQUEST_PARAMETER when orderId is empty in getOrderById`() {
       // given
        val orderId = ""

        // when
        val executable = Executable { orderValidation.validateGetOrderById(orderId = orderId) }

        //then
        val error = assertThrows(MultiErrorException::class.java, executable)
        assertTrue(error.errorCodes.contains(INVALID_REQUEST_PARAMETER))
    }

    @Test
    fun `should throw MultiErrorException contains INVALID_REQUEST_PARAMETER when restaurantId is empty`() {
       // given
        val restaurantId = ""

        // when
        val executable = Executable {
            orderValidation.validateGetOrdersByRestaurantId(restaurantId = restaurantId)
        }

        //then
        val error = assertThrows(MultiErrorException::class.java, executable)
        assertTrue( error.errorCodes.contains(INVALID_REQUEST_PARAMETER))
    }

    @Test
    fun `should throw MultiErrorException contains INVALID_REQUEST_PARAMETER when restaurantId is empty in history order`() {
       // given
        val restaurantId = ""
        val page = 1
        val limit = 10

        // when
        val executable = Executable {
            orderValidation.validateGetOrdersHistory(
                restaurantId = restaurantId,
                page = page,
                limit = limit
            )
        }

        //then
        val error = assertThrows(MultiErrorException::class.java, executable)
        assertTrue( error.errorCodes.contains(INVALID_REQUEST_PARAMETER))
    }


    @Test
    fun `should return true when currentTime is between open and closed time`() {
        // given opening and closing time
        val openTime = "08:00"
        val closeTime = "23:00"
        // when we pass opening and closing time to the function and the current time is between this time
        val result = orderValidation.isRestaurantOpen(openTime = openTime, closeTime = closeTime)
        // then it should return true
        assertTrue(result)
    }

    @Test
    fun `should return false when current time is out of open and closed time`() {
        // given opening and closing time
        val openTime = "09:00"
        val closeTime = "12:00"
        // when we pass opening and closing time to the function and the current time is not between this time
        val result = orderValidation.isRestaurantOpen(openTime = openTime, closeTime = closeTime)
        // then it should return false
        assertFalse(result)
    }
}