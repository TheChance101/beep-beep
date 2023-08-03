package org.thechance.service_restaurant.api.models.mappers

import org.thechance.service_restaurant.api.models.CategoryDto
import org.thechance.service_restaurant.entity.Category


fun CategoryDto.toEntity() = Category(id = id, name = name)