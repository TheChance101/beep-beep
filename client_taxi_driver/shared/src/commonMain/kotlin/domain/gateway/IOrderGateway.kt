package domain.gateway

import domain.entity.Order

interface IOrderGateway {
    suspend fun findingNewOrder(): Order
}