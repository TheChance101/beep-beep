package org.thechance.service_identity.domain.entity

data class Permission(
    val id: String,
    val permission: Int,
    val isDeleted: Boolean = false
)
