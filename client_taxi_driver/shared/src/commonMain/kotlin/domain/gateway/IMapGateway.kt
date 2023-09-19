package domain.gateway

import domain.entity.Order

interface IMapGateway {
    suspend fun findingNewOrder(): Order
}