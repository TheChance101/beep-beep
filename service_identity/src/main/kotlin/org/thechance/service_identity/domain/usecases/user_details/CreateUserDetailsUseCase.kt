package org.thechance.service_identity.domain.usecases.user_details

import org.thechance.service_identity.domain.entity.UserDetails

interface CreateUserDetailsUseCase {

    suspend operator fun invoke(userDetails: UserDetails)

}