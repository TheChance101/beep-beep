package org.thechance.service_identity.endpoints.model.mapper

import org.thechance.service_identity.domain.entity.User
import org.thechance.service_identity.domain.entity.UserManagement
import org.thechance.service_identity.endpoints.model.UserManagementDto

fun UserManagement.toDto() = UserManagementDto(
    id = id,
    fullName = fullName,
    username = username,
    email = email,
    country = country,
    phone = phone,
    permission = permission
)

fun List<UserManagement>.toDto() = map { it.toDto() }

fun UserManagement.toUserDetails(currency: String, walletBalance: Double) = User(
    id = id,
    fullName = fullName,
    username = username,
    email = email,
    country = country,
    phone = phone,
    permission = permission,
    currency = currency,
    walletBalance = walletBalance
)