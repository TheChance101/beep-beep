package data.gateway.remote

import data.remote.mapper.toEntity
import data.remote.model.ServerResponse
import data.remote.model.TicketDto
import domain.entity.Ticket
import domain.gateway.IChatGateway
import domain.utils.GeneralException
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.post
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.util.InternalAPI
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.load

class ChatGateway(client: HttpClient) : BaseGateway(client = client), IChatGateway {
    @OptIn(InternalAPI::class)
    override suspend fun createTicket(userId: String) : Ticket {
        val s =  tryToExecute<TicketDto> {
            post("/chat/ticket") {
                body = Json.encodeToString(TicketDto.serializer(), TicketDto(userId = userId))
            }
        }.toEntity()

        println("ddd $s")
        return s
    }
}