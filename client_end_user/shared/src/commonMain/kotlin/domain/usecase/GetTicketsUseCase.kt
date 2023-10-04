package domain.usecase

import domain.entity.Ticket
import domain.gateway.IChatGateway
import kotlinx.coroutines.flow.Flow

interface IGetTicketsUseCase{
    operator fun invoke(): Flow<Ticket>
}

class GetTicketsUseCase(private val chatGateway: IChatGateway):IGetTicketsUseCase{
    override fun invoke(): Flow<Ticket> {
        return chatGateway.getTickets()
    }

}