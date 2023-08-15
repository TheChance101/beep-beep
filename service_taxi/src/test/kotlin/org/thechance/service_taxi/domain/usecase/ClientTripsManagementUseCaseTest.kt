package org.thechance.service_taxi.domain.usecase

import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.function.Executable
import org.thechance.service_taxi.domain.FakeGateway
import org.thechance.service_taxi.domain.entity.Location
import org.thechance.service_taxi.domain.entity.Trip
import org.thechance.service_taxi.domain.exceptions.INVALID_DATE
import org.thechance.service_taxi.domain.exceptions.INVALID_PRICE
import org.thechance.service_taxi.domain.exceptions.INVALID_RATE
import org.thechance.service_taxi.domain.exceptions.MultiErrorException
import org.thechance.service_taxi.domain.exceptions.ResourceNotFoundException
import org.thechance.service_taxi.domain.usecase.utils.Validations

class ClientTripsManagementUseCaseTest {
    private val clientTripsManagement: IClientTripsManagementUseCase =
        ClientTripsManagementUseCase(FakeGateway, Validations())

    @BeforeEach
    fun reset() {
        FakeGateway.reset()
    }

    // region create trip
    @Test
    fun `should throw MultiErrorException contains error code INVALID_RATE when rate is invalid`() {
        // given trip with negative rate
        val trip = Trip(
            id = "64d111a60f294c4b8f718973",
            driverId = "123456789123456789123471",
            taxiId = "123456789123456789153471",
            clientId = "6123456789123456789123471",
            startPoint = Location(
                latitude = 30.0,
                longitude = 130.0
            ),
            destination = Location(
                latitude = 60.0,
                longitude = 160.0
            ),
            rate = -10.0,
            price = 100.0,
            startDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
            endDate = Clock.System.now().plus(2, DateTimeUnit.HOUR)
                .toLocalDateTime(TimeZone.currentSystemDefault()),
        )
        // when create a new trip
        val result = Executable { runBlocking { clientTripsManagement.createTrip(trip) } }
        // then check
        val throwable = Assertions.assertThrows(MultiErrorException::class.java, result)
        Assertions.assertTrue { throwable.errorCodes.contains(INVALID_RATE) }
    }

    @Test
    fun `should throw MultiErrorException contains error code INVALID_DATE when start date is grater end date`() {
        // given trip with invalid date
        val trip = Trip(
            id = "64d111a60f294c4b8f718973",
            driverId = "123456789123456789123471",
            taxiId = "123456789123456789153471",
            clientId = "6123456789123456789123471",
            startPoint = Location(
                latitude = 30.0,
                longitude = 130.0
            ),
            destination = Location(
                latitude = 60.0,
                longitude = 160.0
            ),
            rate = 5.0,
            price = 100.0,
            startDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
            endDate = Clock.System.now().minus(2, DateTimeUnit.HOUR)
                .toLocalDateTime(TimeZone.currentSystemDefault()),
        )
        // when create a new trip
        val result = Executable { runBlocking { clientTripsManagement.createTrip(trip) } }
        // then check
        val throwable = Assertions.assertThrows(MultiErrorException::class.java, result)
        Assertions.assertTrue { throwable.errorCodes.contains(INVALID_DATE) }
    }

    @Test
    fun `should throw MultiErrorException contains error code INVALID_PRICE when price is invalid`() {
        // given trip with invalid price
        val trip = Trip(
            id = "64d111a60f294c4b8f718973",
            driverId = "123456789123456789123471",
            taxiId = "123456789123456789153471",
            clientId = "6123456789123456789123471",
            startPoint = Location(
                latitude = 20.0,
                longitude = 130.0
            ),
            destination = Location(
                latitude = 20.0,
                longitude = 130.0
            ),
            rate = 5.0,
            price = -100.0,
            startDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
            endDate = Clock.System.now().plus(2, DateTimeUnit.HOUR)
                .toLocalDateTime(TimeZone.currentSystemDefault()),
        )
        // when create a new trip
        val result = Executable { runBlocking { clientTripsManagement.createTrip(trip) } }
        // then check
        val throwable = Assertions.assertThrows(MultiErrorException::class.java, result)
        Assertions.assertTrue { throwable.errorCodes.contains(INVALID_PRICE) }
    }

    @Test
    fun `should create a trip when trip is valid`() {
        FakeGateway.reset()
        // given valid trip
        val trip = Trip(
            id = "64d111a60f294c4b8f718973",
            driverId = "64d111a60f294c4b8f718973",
            taxiId = "64d111a60f294c4b8f718973",
            clientId = "64d111a60f294c4b8f718973",
            startPoint = Location(
                latitude = -30.0,
                longitude = 130.0
            ),
            destination = Location(
                latitude = 30.0,
                longitude = 160.0
            ),
            rate = 5.0,
            price = 100.0,
            startDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
            endDate = Clock.System.now().plus(2, DateTimeUnit.HOUR)
                .toLocalDateTime(TimeZone.currentSystemDefault()),
        )
        // when create a new trip
        val oldSize = FakeGateway.trips.size
        runBlocking { clientTripsManagement.createTrip(trip) }
        // then check
        Assertions.assertTrue { oldSize + 1 == FakeGateway.trips.size }
    }
    // endregion

    // region rate trip
    @Test
    fun `should throw ResourceNotFoundException when id is invalid`() {
        // given invalid id
        val rate = 5.0
        val id = "12313"
        // when rate the trip
        val result = Executable { runBlocking { clientTripsManagement.rateTrip(id, rate) } }
        // check
        Assertions.assertThrows(ResourceNotFoundException::class.java, result)
    }

    @Test
    fun `should throw MultiErrorException with error code INVALID_RATE when rate is invalid`() {
        // given invalid rate
        val rate = -10.0
        val id = FakeGateway.trips.first().id
        // when rate the trip
        val result = Executable { runBlocking { clientTripsManagement.rateTrip(id, rate) } }
        // check
        val throwable = Assertions.assertThrows(MultiErrorException::class.java, result)
        Assertions.assertTrue { throwable.errorCodes.contains(INVALID_RATE) }
    }

    @Test
    fun `should assign rate with rate is valid`() {
        // given valid rate
        val rate = 5.0
        val id = FakeGateway.trips.first().id
        // when rate the trip
        runBlocking { clientTripsManagement.rateTrip(id, rate) }
        // check
        Assertions.assertTrue { FakeGateway.trips.first().rate == rate }
    }
    // endregion
}