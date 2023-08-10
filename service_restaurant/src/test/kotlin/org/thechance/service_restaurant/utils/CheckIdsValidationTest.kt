package org.thechance.service_restaurant.utils

import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.function.Executable

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CheckIdsValidationTest {
    @Test
    fun `should throw MultiErrorException contains INVALID_ID code when id is invalid`() {
        // given invalid id
        val id = "invalid id"
        // when checkIsValidIds is called
        val result = Executable { checkIsValidIds(id, listOf("2a1b3c4d5e6f7a8b9c0d1e2f")) }
        // then check if MultiErrorException contains INVALID_ID code
        val throwable = Assertions.assertThrows(MultiErrorException::class.java, result)
        Assertions.assertTrue(throwable.errorCodes.contains(INVALID_ID))
    }

    @Test
    fun `should throw MultiErrorException contains INVALID_ID code twice when id and listIds are invalid`() {
        // given invalid id and listIds
        val id = "invalid id"
        val listIds = listOf("invalid id", "2a1b3c4d5e6f7a8b9c0d1e2f")
        // when checkIsValidIds is called
        val result = Executable { checkIsValidIds(id, listIds) }
        // then check if MultiErrorException contains INVALID_ID code twice
        val throwable = Assertions.assertThrows(MultiErrorException::class.java, result)
        Assertions.assertTrue(throwable.errorCodes.let { errorCodes -> errorCodes.all { it == INVALID_ID } && errorCodes.size == 2 })
    }

    @Test
    fun `should do nothing when ids are valid`(){
        // given valid id and listIds
        val id = "2a1b3c4d5e6f7a8b9c0d1e2f"
        val listIds = listOf("2a1b3c4d5e6f7a8b9c0d1e2f", "2a1b3c4d5e6f7a8b9c0d1e2e")
        // when checkIsValidIds is called
        val result = Executable { checkIsValidIds(id, listIds) }
        // then check if result has no errors
        Assertions.assertDoesNotThrow(result)
    }
}