package org.thechance.service_restaurant.domain.usecase.validation

import org.koin.core.component.KoinComponent
import org.thechance.service_restaurant.domain.entity.Category
import org.thechance.service_restaurant.domain.utils.IValidation
import org.thechance.service_restaurant.domain.utils.exceptions.*

interface ICategoryValidationUseCase {
    fun validationCategory(category: Category)

}

class CategoryValidationUseCase(
    private val basicValidation: IValidation
) : ICategoryValidationUseCase, KoinComponent {


    override fun validationCategory(category: Category) {
        val validationErrors = mutableListOf<Int>()

        if (!basicValidation.isValidId(category.id)) {
            validationErrors.add(INVALID_ID)
        }
        if (!basicValidation.isValidName(category.name)) {
            validationErrors.add(INVALID_NAME)
        }
        if (validationErrors.isNotEmpty()) {
            throw MultiErrorException(validationErrors)
        }
    }
}

