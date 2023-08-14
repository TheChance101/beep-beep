package org.thechance.service_restaurant.domain.validation

import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.function.Executable
import org.thechance.service_restaurant.domain.usecase.validation.OrderValidationUseCase
import org.thechance.service_restaurant.domain.utils.OrderStatus
import org.thechance.service_restaurant.domain.utils.Validation
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_ID
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_REQUEST_PARAMETER
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_STATUS
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_UPDATE_PARAMETER
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException

class OrderValidationTest{
    private val orderValidation = OrderValidationUseCase(Validation())

    @Test
    fun `should should throw MultiErrorException contains INVALID_ID when orderId is Invalid`() {
        val orderId = "not_good_id"
        val status = OrderStatus.PENDING

        val executable = Executable { orderValidation.validateUpdateOrder(orderId, status) }

        val error = assertThrows(MultiErrorException::class.java, executable)
        assertEquals(true, error.errorCodes.contains(INVALID_ID))
    }

    @Test
    fun `should should throw MultiErrorException contains INVALID_STATUS when status is Invalid`() {
        val orderId = "3edf2fc8-6983-484f-a35c-8190f44a08c6"
        val status = OrderStatus.getOrderStatus(10)

        val executable = Executable { orderValidation.validateUpdateOrder(orderId, status) }

        val error = assertThrows(MultiErrorException::class.java, executable)
        assertEquals(true, error.errorCodes.contains(INVALID_STATUS))
    }

    @Test
    fun `should do nothing when orderStatus is valid`() {
        val orderId = "3edf2fc8-6983-484f-a35c-8190f44a08c6"
        val status = OrderStatus.PENDING

        val executable = Executable { orderValidation.validateUpdateOrder(orderId, status) }

        assertDoesNotThrow(executable)
    }

    @Test
    fun `should throw MultiErrorException contains INVALID_UPDATE_PARAMETER when status is empty `(){
        val orderId = "3edf2fc8-6983-484f-a35c-8190f44a08c6"
        val status = null

        val executable = Executable { status?.let {
            orderValidation.validateUpdateOrder(orderId,
                it
            )
        } }

        val error = assertThrows(MultiErrorException::class.java, executable)
        assertEquals(true, error.errorCodes.contains(INVALID_UPDATE_PARAMETER))
    }

    @Test
    fun `should throw MultiErrorException contains INVALID_REQUEST_PARAMETER when orderId is empty`(){
        val orderId = ""
        val status = OrderStatus.PENDING

        val executable = Executable { orderValidation.validateUpdateOrder(orderId, status) }

        val error = assertThrows(MultiErrorException::class.java, executable)
        assertEquals(true, error.errorCodes.contains(INVALID_REQUEST_PARAMETER))
    }

}