package org.thechance.service_restaurant.domain.validation

import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.function.Executable
import org.thechance.service_restaurant.domain.entity.Category
import org.thechance.service_restaurant.domain.usecase.validation.CategoryValidationUseCase
import org.thechance.service_restaurant.domain.utils.Validation
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_ID
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_NAME
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CategoryValidationsTest {

    private val validationCategory = CategoryValidationUseCase(Validation())


    @Test
    fun `should throw MultiErrorException contains INVALID_ID code when id is invalid`() {
        // given invalid id
        val id = "invalid id"
        val category = Category(id = id, name = "name")
        // when validationCategory is called
        val result = Executable { validationCategory.validationCategory(category) }
        // then check if MultiErrorException contains INVALID_ID code
        val throwable = Assertions.assertThrows(MultiErrorException::class.java, result)
        Assertions.assertTrue(throwable.errorCodes.contains(INVALID_ID))
    }

    @Test
    fun `should throw MultiErrorException contains INVALID_NAME code when name is invalid`() {
        // given invalid name
        val name = "@invalid \$name"
        val category = Category(id = "2a1b3c4d5e6f7a8b9c0d1e2f", name = name)
        // when validationCategory is called
        val result = Executable { validationCategory.validationCategory(category) }
        // then check if MultiErrorException contains INVALID_NAME code
        val throwable = Assertions.assertThrows(MultiErrorException::class.java, result)
        Assertions.assertTrue(throwable.errorCodes.contains(INVALID_NAME))
    }

    @Test
    fun `should throw MultiErrorException contains INVALID_NAME and INVALID_ID codes when name and id are invalid`() {
        // given invalid name and id
        val name = "@invalid \$name"
        val id = "invalid id"
        val category = Category(id = id, name = name)
        // when validationCategory is called
        val result = Executable { validationCategory.validationCategory(category) }
        // then check if MultiErrorException contains INVALID_NAME and INVALID_ID codes
        val throwable = Assertions.assertThrows(MultiErrorException::class.java, result)
        Assertions.assertTrue(throwable.errorCodes.containsAll(listOf(INVALID_NAME, INVALID_ID)))
    }

    @Test
    fun `should do nothing when category is valid`() {
        // given valid category
        val category = Category(id = "6BFC9A2D8E15C037F921D4A6", name = "name")
        // when validationCategory is called
        val result = Executable { validationCategory.validationCategory(category) }
        // then check if result has no errors
        Assertions.assertDoesNotThrow(result)
    }

}