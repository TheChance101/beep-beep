package org.thechance.service_taxi.domain.usecase

import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.function.Executable
import org.thechance.service_taxi.domain.FakeGateway
import org.thechance.service_taxi.domain.exceptions.ResourceNotFoundException

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ManageTripsUseCaseTest {
    private val manageTripsUseCase: IManageTripsUseCase =
        ManageTripsUseCase(FakeGateway)

    @BeforeEach
    fun reset() {
        FakeGateway.reset()
    }

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