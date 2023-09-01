package org.thechance.api_gateway.data.model

import kotlinx.serialization.Serializable

/**
 * Created by Aziza Helmy on 8/14/2023.
 */

@Serializable
data class User(
    val id: String,
    val fullName: String,
    val username: String,
    val email: String,
    val permission: Int
)