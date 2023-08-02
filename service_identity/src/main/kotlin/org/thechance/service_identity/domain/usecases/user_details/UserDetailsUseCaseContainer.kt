package org.thechance.service_identity.domain.usecases.user_details

import org.koin.core.annotation.Single

@Single
data class UserDetailsUseCaseContainer(
    val removeAddressFromUser: RemoveAddressFromUserUseCase,
    val addPermissionToUser: AddPermissionToUserUseCase,
    val addAddressToUser: AddAddressToUserUseCase,
    val createUserDetails: CreateUserDetailsUseCase,
    val removePermissionFromUser: RemovePermissionFromUserUseCase,
    val getUserDetails: GetUserDetailsUseCase,
    val updateUserDetails: UpdateUserDetailsUseCase,
    val addWalletToUser: AddWalletToUserUseCase,
    val removeWalletFromUser: DeleteUserWalletUseCase
)
