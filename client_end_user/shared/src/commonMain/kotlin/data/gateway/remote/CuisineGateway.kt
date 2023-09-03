package data.gateway.remote

import data.remote.mapper.toEntity
import data.remote.model.CuisineDto
import data.remote.model.ServerResponse
import domain.entity.Cuisine
import domain.gateway.ICuisineRemoteGateway
import domain.utils.NotFoundException
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class CuisineGateway(client: HttpClient) : BaseGateway(client = client), ICuisineRemoteGateway {
    override suspend fun getCuisines(): List<Cuisine> {
        return tryToExecute<ServerResponse<List<CuisineDto>>> {
            get("/cuisines")
        }.value?.toEntity() ?: throw NotFoundException()

    }
}