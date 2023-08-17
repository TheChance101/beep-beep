package org.thechance.service_taxi.domain.usecase

import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.function.Executable
import org.thechance.service_taxi.domain.FakeGateway
import org.thechance.service_taxi.domain.exceptions.ResourceNotFoundException

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DriverTripsManagementTripsUseCaseTest {
    private val driverTripsManagement: IDriverTripsManagementUseCase =
        DriverTripsManagementUseCase(FakeGateway)

    // region approve trip
    @Test
    fun `should throw ResourceNotFoundException when id not in our system`() {
        // given id not in system
        val tripId = "64d111a60f294c4b8f718977"
        val driverId = "64d111a60f294c4b8f718977"
        val taxiId = "64d111a60f294c4b8f718977"

        // when approve trip
        val result =
            Executable {
                runBlocking {
                    driverTripsManagement.approveTrip(
                        driverId,
                        taxiId,
                        tripId
                    )
                }
            }
        // then check
        Assertions.assertThrows(ResourceNotFoundException::class.java, result)
    }

    @Test
    fun `should return updated trip when givens are valid`() {
        // given id in system
        val tripId = FakeGateway.trips.first().id
        val driverId = "64d111a60f294c4b8f718977"
        val taxiId = "64d111a60f294c4b8f718978"
        // when approve trip
        val target = FakeGateway.trips.first()
        val result = runBlocking {
            driverTripsManagement.approveTrip(
                driverId,
                taxiId,
                tripId
            )
        }
        // check check
        Assertions.assertEquals(
            target.copy(
                driverId = driverId,
                taxiId = taxiId,
                startDate = result.startDate
            ), result
        )
    }
    // endregion

    // region finish trip
    @Test
    fun `should throw ResourceNotFoundException when id not in our system in finish`() {
        // given id not in system
        val tripId = "64d111a60f294c4b8f718977"
        val driverId = "64d111a60f294c4b8f718977"
        // when finish trip
        val result =
            Executable { runBlocking { driverTripsManagement.finishTrip(driverId, tripId) } }
        // then check
        Assertions.assertThrows(ResourceNotFoundException::class.java, result)
    }

    @Test
    fun `should return trip when finish trip`(){
        // given id in system
        val tripId = FakeGateway.trips.first().id
        val driverId = "64d111a60f294c4b8f718977"
        // when finish trip
        val target = FakeGateway.trips.first()
        val result = runBlocking { driverTripsManagement.finishTrip(driverId, tripId) }
        // check
        Assertions.assertEquals(target.copy(endDate = result.endDate), result)
    }
    // endregion
}