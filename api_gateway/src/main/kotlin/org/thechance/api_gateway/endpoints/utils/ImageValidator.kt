package org.thechance.api_gateway.endpoints.utils

import org.koin.core.annotation.Single


@Single
class ImageValidator {
    fun isValid(name: String?) : Boolean {
        val extension = name?.substringAfterLast(".", "")
        val isImage = extension?.let { it in listOf("jpg", "jpeg", "png") } ?: false
        return if (isImage) true else throw Exception("Invalid File")
    }
}