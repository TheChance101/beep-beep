package org.thechance.service_identity.entity

data class User(
    val id: String,
    val fullName: String,
    val username: String,
    val isDeleted: Boolean
)
