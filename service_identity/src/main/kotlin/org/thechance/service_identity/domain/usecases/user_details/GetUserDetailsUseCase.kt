package org.thechance.service_identity.domain.usecases.user_details

import org.thechance.service_identity.domain.entity.UserDetails

interface GetUserDetailsUseCase {

    suspend operator fun invoke(userId: String): UserDetails

}