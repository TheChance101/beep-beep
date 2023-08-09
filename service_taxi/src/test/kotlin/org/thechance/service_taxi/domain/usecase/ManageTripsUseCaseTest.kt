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
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.function.Executable
import org.thechance.service_taxi.domain.FakeGateway
import org.thechance.service_taxi.domain.entity.Location
import org.thechance.service_taxi.domain.entity.Trip
import org.thechance.service_taxi.domain.util.INVALID_DATE
import org.thechance.service_taxi.domain.util.INVALID_LOCATION
import org.thechance.service_taxi.domain.util.INVALID_PRICE
import org.thechance.service_taxi.domain.util.INVALID_RATE
import org.thechance.service_taxi.domain.util.MultiErrorException
import org.thechance.service_taxi.domain.util.ResourceNotFoundException
import org.thechance.service_taxi.domain.util.Validations

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ManageTripsUseCaseTest {
    private val manageTripsUseCase: IManageTripsUseCase =
        ManageTripsUseCase(FakeGateway, Validations())

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
        val result = Executable { runBlocking { manageTripsUseCase.createTrip(trip) } }
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
        val result = Executable { runBlocking { manageTripsUseCase.createTrip(trip) } }
        // then check
        val throwable = Assertions.assertThrows(MultiErrorException::class.java, result)
        Assertions.assertTrue { throwable.errorCodes.contains(INVALID_DATE) }
    }

    @Test
    fun `should throw MultiErrorException contains error code INVALID_PRICE,INVALID_LOCATION when price and location are invalid`() {
        // given trip with invalid price and location
        val trip = Trip(
            id = "64d111a60f294c4b8f718973",
            driverId = "123456789123456789123471",
            taxiId = "123456789123456789153471",
            clientId = "6123456789123456789123471",
            startPoint = Location(
                latitude = 2000.0,
                longitude = 2000.0
            ),
            destination = Location(
                latitude = 400.0,
                longitude = 600.0
            ),
            rate = 5.0,
            price = -100.0,
            startDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
            endDate = Clock.System.now().plus(2, DateTimeUnit.HOUR)
                .toLocalDateTime(TimeZone.currentSystemDefault()),
        )
        // when create a new trip
        val result = Executable { runBlocking { manageTripsUseCase.createTrip(trip) } }
        // then check
        val throwable = Assertions.assertThrows(MultiErrorException::class.java, result)
        Assertions.assertTrue {
            throwable.errorCodes.containsAll(
                listOf(
                    INVALID_PRICE,
                    INVALID_LOCATION
                )
            )
        }
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
        runBlocking { manageTripsUseCase.createTrip(trip) }
        // then check
        Assertions.assertTrue { oldSize + 1 == FakeGateway.trips.size }
    }
    // endregion

    // region get trip
    @Test
    fun `should throw ResourceNotFoundException when id not in our system`() {
        // given id not in system
        val tripId = "64d111a60f294c4b8f718977"
        // when get trip
        val result = Executable { runBlocking { manageTripsUseCase.getTripById(tripId) } }
        // then check
        Assertions.assertThrows(ResourceNotFoundException::class.java, result)
    }

    @Test
    fun `should return correct trip when id in our system`() {
        // given id
        val tripId = FakeGateway.trips.first().id
        // when get trip
        val target = FakeGateway.trips.first()
        val result = runBlocking { manageTripsUseCase.getTripById(tripId) }
        // check
        Assertions.assertEquals(target, result)
    }

    @Test
    fun `should return correct trips when trips are in our system`() {
        // given page and limit
        val page = 1
        val limit = 5
        // get trips
        val trips = FakeGateway.trips.toTypedArray()
        val result = runBlocking { manageTripsUseCase.getTrips(page, limit) }
        // check
        Assertions.assertArrayEquals(trips, result.toTypedArray())
    }
    // endregion
}