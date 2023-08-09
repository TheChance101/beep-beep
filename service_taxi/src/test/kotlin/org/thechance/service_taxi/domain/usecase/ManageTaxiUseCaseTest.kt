package org.thechance.service_taxi.domain.usecase

import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.function.Executable
import org.thechance.service_taxi.domain.FakeGateway
import org.thechance.service_taxi.domain.entity.Taxi
import org.thechance.service_taxi.domain.util.INVALID_ID
import org.thechance.service_taxi.domain.util.INVALID_PLATE
import org.thechance.service_taxi.domain.util.MultiErrorException
import org.thechance.service_taxi.domain.util.Validations

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ManageTaxiUseCaseTest {
    private val administratorUseCase: IManageTaxiUseCase = ManageTaxiUseCase(
        FakeGateway,
        Validations()
    )

    @Test
    fun `should throw MultiErrorException contains error code INVALID_PLATE when plate number is invalid`() {
        // given taxi with invalid plate
        val taxi = Taxi(
            id = "64d111a60f294c4b8f718973",
            plateNumber = "1234amsm",
            color = "Red",
            type = "type",
            driverId = "123456789123456789123471",
            isAvailable = true,
            seats = 4
        )
        // when create a new taxi
        val result = Executable {
            runBlocking {
                administratorUseCase.createTaxi(taxi)
            }
        }
        // then check
        val throwable = Assertions.assertThrows(MultiErrorException::class.java, result)
        Assertions.assertTrue { throwable.errorCodes.contains(INVALID_PLATE) }
    }

    @Test
    fun `should throw MultiErrorException contains error code INVALID_PLATE and INVALID_ID when plate number and driver id is invalid`() {
        // given taxi with invalid plate and driver id in length
        val taxi = Taxi(
            id = "64d111a60f294c4b8f718973",
            plateNumber = "1234amsm",
            color = "Red",
            type = "type",
            driverId = "12345678912345678912347",
            isAvailable = true,
            seats = 4
        )
        // when create a new taxi
        val result = Executable {
            runBlocking {
                administratorUseCase.createTaxi(taxi)
            }
        }
        // then check
        val throwable = Assertions.assertThrows(MultiErrorException::class.java, result)
        Assertions.assertTrue {
            throwable.errorCodes.containsAll(
                listOf(
                    INVALID_PLATE,
                    INVALID_ID
                )
            )
        }
    }
}
