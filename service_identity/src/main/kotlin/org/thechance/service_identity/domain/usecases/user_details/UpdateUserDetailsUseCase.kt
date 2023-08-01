package org.thechance.service_identity.domain.usecases.user_details

import org.thechance.service_identity.domain.entity.UserDetails

interface UpdateUserDetailsUseCase {

    suspend operator fun invoke(userDetails: UserDetails)

}