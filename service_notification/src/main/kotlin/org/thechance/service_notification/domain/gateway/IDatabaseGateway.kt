package org.thechance.service_notification.domain.gateway


interface IDatabaseGateway {
    suspend fun getUserTokensById(id: String): List<String>

}