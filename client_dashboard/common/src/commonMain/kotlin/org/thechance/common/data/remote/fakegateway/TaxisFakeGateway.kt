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
        limit: Int
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

        val startIndex = (page - 1) * limit
        val endIndex = startIndex + limit
        val numberOfPages = ceil(taxis.size / (limit * 1.0)).toInt()
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
                color = taxiDto.color.toLong(),
                seats = taxiDto.seats,
                type = taxiDto.type,
                username = taxiDto.driverId,
                isAvailable = false,
                trips = "0",
                driverId = taxiDto.driverId
            )
        )
        return taxis.last().toEntity()
    }

    override suspend fun updateTaxi(taxi: NewTaxiInfo): Taxi {
        val indexToUpdate = taxis.indexOfFirst { it.driverId == taxi.driverUserName }
        val newTaxi = taxi.toDto()
        return if (indexToUpdate != -1) {
            val oldTaxi = taxis[indexToUpdate]
            val updatedTaxi = TaxiDto(
                id = UUID.randomUUID().toString(),
                plateNumber = newTaxi.plateNumber,
                color = newTaxi.color.toLong(),
                type = newTaxi.type,
                seats = newTaxi.seats,
                username = newTaxi.driverId,
                trips = oldTaxi.trips,
                isAvailable = oldTaxi.isAvailable,
                driverId = oldTaxi.driverId
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
            isAvailable = true,
            username = "john_doe",
            trips = "10",
            driverId = "DRIVER_ID",
        ),
        TaxiDto(
            id = "2",
            plateNumber = "XYZ789",
            color = 2,
            type = "SUV",
            seats = 6,
            username = "jane_doe",
            isAvailable = true,
            trips = "5",
            driverId = "DRIVER_ID"
        ),
        TaxiDto(
            id = "3",
            plateNumber = "DEF456",
            color = 3,
            type = "Hatchback",
            seats = 4,
            username = "james_smith",
            isAvailable = true,
            trips = "2",
            driverId = "DRIVER_ID"
        ),
        TaxiDto(
            id = "4",
            plateNumber = "GHI789",
            color = 4,
            type = "Minivan",
            seats = 6,
            username = "mary_johnson",
            isAvailable = true,
            trips = "15",
            driverId = "DRIVER_ID"
        ),
        TaxiDto(
            id = "5",
            plateNumber = "JKL012",
            color = 1,
            type = "Convertible",
            seats = 4,
            username = "robert_white",
            isAvailable = true,
            trips = "3",
            driverId = "DRIVER_ID"
        ),
        TaxiDto(
            id = "6",
            plateNumber = "MNO345",
            color = 2,
            type = "Sedan",
            seats = 4,
            username = "linda_miller",
            isAvailable = true,
            trips = "7",
            driverId = "DRIVER_ID"
        ),
        TaxiDto(
            id = "7",
            plateNumber = "PQR678",
            color = 3,
            type = "Hatchback",
            seats = 4,
            username = "david_jones",
            isAvailable = true,
            trips = "12",
            driverId = "DRIVER_ID"
        ),
        TaxiDto(
            id = "8",
            plateNumber = "STU901",
            color = 4,
            type = "Minivan",
            seats = 2,
            username = "susan_anderson",
            isAvailable = true,
            trips = "9",
            driverId = " DRIVER_ID"
        ),
        TaxiDto(
            id = "9",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 1,
            seats = 4,
            isAvailable = false,
            username = "Asia",
            trips = "10",
            driverId = "DRIVER_ID"
        ),
        TaxiDto(
            id = "10",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 1,
            seats = 4,
            isAvailable = false,
            username = "Safi",
            trips = "10",
            driverId = "DRIVER_ID"
        ),
        TaxiDto(
            id = "11",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 1,
            seats = 4,
            isAvailable = false,
            username = "Mujtaba",
            trips = "10",
            driverId = "DRIVER_ID"
        ),
        TaxiDto(
            id = "12",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 1,
            seats = 4,
            isAvailable = false,
            username = "Krrar",
            trips = "10",
            driverId = "DRIVER_ID"
        ),
        TaxiDto(
            id = "13",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 1,
            seats = 4,
            isAvailable = false,
            username = "Aya",
            trips = "10",
            driverId = "DRIVER_ID"
        ),
        TaxiDto(
            id = "14",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 1,
            seats = 4,
            isAvailable = false,
            username = "Kamel",
            trips = "10",
            driverId = "DRIVER_ID"
        ),
        TaxiDto(
            id = "16",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 2,
            seats = 4,
            isAvailable = false,
            username = "Kamel",
            trips = "10",
            driverId = "DRIVER_ID"
        ),
        TaxiDto(
            id = "17",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 2,
            seats = 5,
            isAvailable = false,
            username = "Kamel",
            trips = "10",
            driverId = "DRIVER_ID"
        ),
        TaxiDto(
            id = "18",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 2,
            seats = 4,
            isAvailable = false,
            username = "Kamel",
            trips = "10",
            driverId = "DRIVER_ID"
        ),
        TaxiDto(
            id = "19",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 2,
            seats = 4,
            isAvailable = true,
            username = "Kamel",
            trips = "10",
            driverId = "DRIVER_ID"
        ),
        TaxiDto(
            id = "20",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 5,
            seats = 5,
            isAvailable = true,
            username = "Kamel",
            trips = "10",
            driverId = "DRIVER_ID"
        ),
        TaxiDto(
            id = "21",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 4,
            seats = 4,
            isAvailable = true,
            username = "Kamel",
            trips = "10",
            driverId = "DRIVER_ID"
        ),
        TaxiDto(
            id = "22",
            plateNumber = "ABC123",
            type = "Sedan",
            color = 5,
            seats = 4,
            isAvailable = true,
            username = "Kamel",
            trips = "10",
            driverId = "DRIVER_ID"
        ),
    )

}