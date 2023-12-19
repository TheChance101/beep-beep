package domain.gateway

import domain.entity.Trip

interface IOrderGateway {
    suspend fun findingNewOrder(): Trip
}