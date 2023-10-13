package domain.usecase

import domain.entity.User
import domain.gateway.IUserGateway


interface IManageProfileUseCase {
    suspend fun getUserProfile(): User

    suspend fun updateUserProfile(name: String?, phoneNumber: String?): User

}

class ManageProfileUseCase(private val remoteGateway: IUserGateway) : IManageProfileUseCase {
    override suspend fun getUserProfile(): User {
        return remoteGateway.getUserProfile()
    }

    override suspend fun updateUserProfile(name: String?, phoneNumber: String?): User {
        return remoteGateway.updateProfile(name, phoneNumber)
    }

}
