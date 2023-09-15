package org.thechance.common.data.remote.gateway

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.thechance.common.data.remote.mapper.toDto
import org.thechance.common.data.remote.mapper.toEntity
import org.thechance.common.data.remote.model.PaginationResponse
import org.thechance.common.data.remote.model.ServerResponse
import org.thechance.common.data.remote.model.TaxiDto
import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.NewTaxiInfo
import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.entity.TaxiFiltration
import org.thechance.common.domain.getway.ITaxisGateway
import org.thechance.common.domain.util.NotFoundException

class TaxisGateway(private val client: HttpClient) : BaseGateway(), ITaxisGateway {

    override suspend fun getTaxis(
        username: String?,
        taxiFiltration: TaxiFiltration,
        page: Int,
        limit: Int
    ): DataWrapper<Taxi> {
        val result = tryToExecute<ServerResponse<PaginationResponse<TaxiDto>>>(client) {
            get(urlString = "/taxi") {
                parameter("page", page)
                parameter("limit", limit)
            }
        }.value

        return DataWrapper(
            totalPages = result?.total?.div(limit) ?: 0,
            numberOfResult = result?.total ?: 0,
            result = result?.items?.toEntity() ?: throw UnknownError()
        )
    }

    override suspend fun createTaxi(taxi: NewTaxiInfo): Taxi {
        val result = tryToExecute<ServerResponse<TaxiDto>>(client) {
            post(urlString = "/taxi") {
                contentType(ContentType.Application.Json)
                setBody(taxi.toDto())
            }
        }.value
        return result?.toEntity() ?: throw UnknownError()
    }

    override suspend fun updateTaxi(taxi: NewTaxiInfo): Taxi {
        TODO("updateTaxi")
    }

    override suspend fun deleteTaxi(taxiId: String): Boolean {
        TODO("deleteTaxi")
    }

    override suspend fun getTaxiById(taxiId: String): Taxi {
        return tryToExecute<ServerResponse<TaxiDto>>(client) {
            get(urlString = "/taxi") { url { appendPathSegments(taxiId) } }
        }.value?.toEntity() ?: throw NotFoundException("Taxi not found")
    }

}