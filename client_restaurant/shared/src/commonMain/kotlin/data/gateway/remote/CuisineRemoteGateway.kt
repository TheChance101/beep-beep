package data.gateway.remote

import data.remote.mapper.toEntity
import data.remote.model.BaseResponse
import data.remote.model.CuisineDto
import domain.entity.Cuisine
import domain.gateway.remote.ICuisineRemoteGateway
import io.ktor.client.HttpClient
import io.ktor.client.request.get


class CuisineRemoteGateway(client: HttpClient) : ICuisineRemoteGateway,
    BaseRemoteGateway(client = client) {
    override suspend fun getCuisines(): List<Cuisine> {
        return tryToExecute<BaseResponse<List<CuisineDto>>> {
            get("/cuisines")
        }.value?.toEntity() ?: emptyList()
    }

}