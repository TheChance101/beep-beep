package org.thechance.service_taxi.domain.usecase

import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.function.Executable
import org.thechance.service_taxi.domain.FakeGateway
import org.thechance.service_taxi.domain.util.INVALID_RATE
import org.thechance.service_taxi.domain.util.MultiErrorException
import org.thechance.service_taxi.domain.util.ResourceNotFoundException
import org.thechance.service_taxi.domain.util.Validations

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DiscoverTripsUseCaseTest {
    private val discoverTripsUseCase: IDiscoverTripsUseCase =
        DiscoverTripsUseCase(FakeGateway, Validations())

    @BeforeEach
    fun reset() {
        FakeGateway.reset()
    }

    // region rate trip
    @Test
    fun `should throw ResourceNotFoundException when id is invalid`() {
        // given invalid id
        val rate = 5.0
        val id = "12313"
        // when rate the trip
        val result = Executable { runBlocking { discoverTripsUseCase.rateTrip(id, rate) } }
        // check
        Assertions.assertThrows(ResourceNotFoundException::class.java, result)
    }

    @Test
    fun `should throw MultiErrorException with error code INVALID_RATE when rate is invalid`() {
        // given invalid rate
        val rate = -10.0
        val id = FakeGateway.trips.first().id
        // when rate the trip
        val result = Executable { runBlocking { discoverTripsUseCase.rateTrip(id, rate) } }
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
        runBlocking { discoverTripsUseCase.rateTrip(id, rate) }
        // check
        Assertions.assertTrue { FakeGateway.trips.first().rate == rate }
    }
    // endregion
}