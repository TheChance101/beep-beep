package data.gateway.remote

import domain.gateway.IIdentityRemoteGateway
import io.ktor.client.HttpClient


class IdentityRemoteGateway(client: HttpClient) : IIdentityRemoteGateway,
    BaseRemoteGateway(client = client) {



}