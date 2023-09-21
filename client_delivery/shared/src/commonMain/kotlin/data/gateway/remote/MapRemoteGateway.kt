package data.gateway.remote

import domain.gateway.remote.IMapRemoteGateway
import io.ktor.client.HttpClient


class MapRemoteGateway(client: HttpClient) : IMapRemoteGateway,
    BaseRemoteGateway(client = client) {

}