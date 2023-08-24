package domain.usecase

import domain.entity.Cousin
import domain.gateway.IRemoteGateWay

interface IGetCousinUseCase {
    suspend operator fun invoke(id:String): List<Cousin>
}

class GetCousinUseCase(private val remoteGateWay: IRemoteGateWay) : IGetCousinUseCase{
    override suspend fun invoke(id:String): List<Cousin> {
        return remoteGateWay.getCousins(id)
    }
}