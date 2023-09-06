package org.thechance.common.data.remote.fakegateway

import org.thechance.common.data.remote.mapper.toDto
import org.thechance.common.data.remote.mapper.toEntity
import org.thechance.common.data.remote.model.DataWrapperDto
import org.thechance.common.data.remote.model.TaxiDto
import org.thechance.common.data.remote.model.toEntity
import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.NewTaxiInfo
import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.entity.TaxiFiltration
import org.thechance.common.domain.getway.ITaxisGateway
import java.util.*
import kotlin.math.ceil

class TaxisFakeGateway : ITaxisGateway {

    override suspend fun getTaxis(
        username: String?,
        taxiFiltration: TaxiFiltration,
        page: Int,
        numberOfTaxis: Int
    ): DataWrapper<Taxi> {
        var filteredTaxis = taxis.toEntity()

        username?.let { name ->
            if (name.isNotEmpty()) {
                filteredTaxis = filteredTaxis.filter {
                    it.username.startsWith(name, ignoreCase = true)
                }
            }
        }

        if (taxiFiltration.status != null || taxiFiltration.carColor != null || taxiFiltration.seats != -1) {
            filteredTaxis = filteredTaxis.filter {
                it.status == taxiFiltration.status &&
                        it.color == taxiFiltration.carColor &&
                        it.seats == taxiFiltration.seats
            }
        }

        val startIndex = (page - 1) * numberOfTaxis
        val endIndex = startIndex + numberOfTaxis
        val numberOfPages = ceil(taxis.size / (numberOfTaxis * 1.0)).toInt()
        return try {
            DataWrapperDto(
                totalPages = numberOfPages,
                result = filteredTaxis.subList(startIndex, endIndex.coerceAtMost(taxis.size)),
                totalResult = filteredTaxis.size
            ).toEntity()
        } catch (e: Exception) {
            DataWrapperDto(
                totalPages = numberOfPages,
                result = filteredTaxis,
                totalResult = filteredTaxis.size
            ).toEntity()
        }
    }

    override suspend fun createTaxi(taxi: NewTaxiInfo): Taxi {
        val taxiDto = taxi.toDto()
        taxis.add(
            TaxiDto(
                id = UUID.randomUUID().toString(),
                plateNumber = taxiDto.plateNumber,
                color = taxiDto.color,
                type = taxiDto.type,
                seats = taxiDto.seats,
                username = taxiDto.username,
                status = null,
                trips = null
            )
        )
        return taxis.last().toEntity()
    }

    override suspend fun updateTaxi(taxi: NewTaxiInfo): Taxi {
        val indexToUpdate = taxis.indexOfFirst { it.id == taxi.id }
        val newTaxi = taxi.toDto()
        return if (indexToUpdate != -1) {
            val oldTaxi = taxis[indexToUpdate]
            val updatedTaxi = TaxiDto(
                id = newTaxi.id,
                plateNumber = newTaxi.plateNumber,
                color = newTaxi.color,
                type = newTaxi.type,
                seats = newTaxi.seats,
                username = newTaxi.username,
                trips = oldTaxi.trips,
                status = oldTaxi.status
            )
            taxis.removeAt(indexToUpdate)
            taxis.add(index = indexToUpdate, element = updatedTaxi)
            updatedTaxi.toEntity()
        } else {
            throw Exception("Taxi not found")
        }
    }

    override suspend fun deleteTaxi(taxiId: String): Boolean {
        val indexToUpdate = taxis.indexOfFirst { it.id == taxiId }
        return if (indexToUpdate != -1) {
            taxis.removeAt(indexToUpdate)
            true
        } else {
            throw Exception("Taxi not found")
        }
    }


    private val taxis = mutableListOf(
        TaxiDto(
            id = "1",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 1,
            seats = 4,
            status = 1,
            username = "john_doe",
            trips = "10"
        ),
        TaxiDto(
            id = "2",
            plateNumber = "XYZ789",
            color = 2,
            type = "SUV",
            seats = 6,
            username = "jane_doe",
            status = 1,
            trips = "5"
        ),
        TaxiDto(
            id = "3",
            plateNumber = "DEF456",
            color = 3,
            type = "Hatchback",
            seats = 4,
            username = "james_smith",
            status = 0,
            trips = "2"
        ),
        TaxiDto(
            id = "4",
            plateNumber = "GHI789",
            color = 4,
            type = "Minivan",
            seats = 6,
            username = "mary_johnson",
            status = 1,
            trips = "15"
        ),
        TaxiDto(
            id = "5",
            plateNumber = "JKL012",
            color = 1,
            type = "Convertible",
            seats = 4,
            username = "robert_white",
            status = 0,
            trips = "3"
        ),
        TaxiDto(
            id = "6",
            plateNumber = "MNO345",
            color = 2,
            type = "Sedan",
            seats = 4,
            username = "linda_miller",
            status = 0,
            trips = "7"
        ),
        TaxiDto(
            id = "7",
            plateNumber = "PQR678",
            color = 3,
            type = "Hatchback",
            seats = 4,
            username = "david_jones",
            status = 1,
            trips = "12"
        ),
        TaxiDto(
            id = "8",
            plateNumber = "STU901",
            color = 4,
            type = "Minivan",
            seats = 2,
            username = "susan_anderson",
            status = 0,
            trips = "9"
        ),
        TaxiDto(
            id = "9",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 1,
            seats = 4,
            status = 1,
            username = "Asia",
            trips = "10"
        ),
        TaxiDto(
            id = "10",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 1,
            seats = 4,
            status = 1,
            username = "Safi",
            trips = "10"
        ),
        TaxiDto(
            id = "11",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 1,
            seats = 4,
            status = 1,
            username = "Mujtaba",
            trips = "10"
        ),
        TaxiDto(
            id = "12",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 1,
            seats = 4,
            status = 1,
            username = "Krrar",
            trips = "10"
        ),
        TaxiDto(
            id = "13",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 1,
            seats = 4,
            status = 1,
            username = "Aya",
            trips = "10"
        ),
        TaxiDto(
            id = "14",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 1,
            seats = 4,
            status = 1,
            username = "Kamel",
            trips = "10"
        ),
        TaxiDto(
            id = "16",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 2,
            seats = 4,
            status = 1,
            username = "Kamel",
            trips = "10"
        ),
        TaxiDto(
            id = "17",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 2,
            seats = 5,
            status = 1,
            username = "Kamel",
            trips = "10"
        ),
        TaxiDto(
            id = "18",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 2,
            seats = 4,
            status = 1,
            username = "Kamel",
            trips = "10"
        ),
        TaxiDto(
            id = "19",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 2,
            seats = 4,
            status = 1,
            username = "Kamel",
            trips = "10"
        ),
        TaxiDto(
            id = "20",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 5,
            seats = 5,
            status = 1,
            username = "Kamel",
            trips = "10"
        ),
        TaxiDto(
            id = "21",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 4,
            seats = 4,
            status = 1,
            username = "Kamel",
            trips = "10"
        ),
        TaxiDto(
            id = "22",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 5,
            seats = 4,
            status = 1,
            username = "Kamel",
            trips = "10"
        ),
    )

}