package org.thechance.service_taxi.domain.usecase

import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.function.Executable
import org.thechance.service_taxi.domain.FakeGateway
import org.thechance.service_taxi.domain.entity.Color
import org.thechance.service_taxi.domain.entity.Taxi
import org.thechance.service_taxi.domain.exceptions.AlreadyExistException
import org.thechance.service_taxi.domain.exceptions.INVALID_ID
import org.thechance.service_taxi.domain.exceptions.INVALID_PLATE
import org.thechance.service_taxi.domain.exceptions.InvalidIdException
import org.thechance.service_taxi.domain.exceptions.MultiErrorException
import org.thechance.service_taxi.domain.exceptions.ResourceNotFoundException
import org.thechance.service_taxi.domain.usecase.utils.Validations

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ManageTaxiUseCaseTest {
    private val manageTaxiUseCase: IManageTaxiUseCase = ManageTaxiUseCase(FakeGateway, Validations())

    @BeforeEach
    fun reset(){
        FakeGateway.reset()
    }

    // region create taxi
    @Test
    fun `should throw MultiErrorException contains error code INVALID_PLATE when plate number is invalid`() {
        // given taxi with invalid plate
        val taxi = Taxi(
            id = "64d111a60f294c4b8f718973",
            plateNumber = "1234amsm",
            color = Color.BLACK,
            type = "type",
            driverId = "123456789123456789123471",
            isAvailable = true,
            seats = 4
        )
        // when create a new taxi
        val result = Executable {
            runBlocking {
                manageTaxiUseCase.createTaxi(taxi)
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
            color = Color.BLACK,
            type = "type",
            driverId = "12345678912345678912347",
            isAvailable = true,
            seats = 4
        )
        // when create a new taxi
        val result = Executable {
            runBlocking {
                manageTaxiUseCase.createTaxi(taxi)
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

    @Test
    fun `should throw AlreadyExistException when add same taxi`() {
        FakeGateway.reset()
        // given a valid taxi
        val taxi = Taxi(
            id = "64d111a60f294c4b8f718977",
            plateNumber = "1234 ABC",
            color = Color.BLACK,
            type = "type",
            driverId = "123456789123456789123471",
            isAvailable = true,
            seats = 4
        )
        // create a new taxi first time
        runBlocking { manageTaxiUseCase.createTaxi(taxi) }
        // try to add same taxi
        val result = Executable { runBlocking { manageTaxiUseCase.createTaxi(taxi) } }
        // then check
        Assertions.assertThrows(AlreadyExistException::class.java, result)
    }

    @Test
    fun `should add taxi when taxi is valid`() {
        // given a valid taxi
        val taxi = Taxi(
            id = "64d111a60f294c4b8f718977",
            plateNumber = "1234 ABC",
            color = Color.BLACK,
            type = "type",
            driverId = "123456789123456789123471",
            isAvailable = true,
            seats = 4
        )
        val oldSize = FakeGateway.taxes.size
        // when create a new taxi
        runBlocking { manageTaxiUseCase.createTaxi(taxi) }
        // then check that taxi added
        Assertions.assertTrue { FakeGateway.taxes.size == oldSize + 1 }
    }
    // endregion

    // region delete taxi
    @Test
    fun `should throw ResourceNotFoundException when taxi not found`() {
        // given taxi id not in system
        val taxiId = "64d111a60f294c4b8f718979"
        // when delete taxi
        val result = Executable { runBlocking { manageTaxiUseCase.deleteTaxi(taxiId) } }
        // then check
        Assertions.assertThrows(ResourceNotFoundException::class.java, result)
    }

    @Test
    fun `should throw InvalidIdException when id is invalid`() {
        // given taxi id not valid
        val taxiId = "64d111a60f294c4b8"
        // when delete taxi
        val result = Executable { runBlocking { manageTaxiUseCase.deleteTaxi(taxiId) } }
        // then check
        Assertions.assertThrows(InvalidIdException::class.java, result)
    }

    @Test
    fun `should delete correctly when id is valid`() {
        // valid id
        val taxiId = FakeGateway.taxes.first().id
        // when delete taxi
        val oldSize = FakeGateway.taxes.size
        runBlocking { manageTaxiUseCase.deleteTaxi(taxiId) }
        // check
        Assertions.assertTrue { FakeGateway.taxes.size == oldSize - 1 }
    }
    // endregion

    // region get taxis
    @Test
    fun `should throw ResourceNotFoundException when taxi id is invalid when get`() {
        // given invalid taxi id
        val taxiId = "a123"
        // when get taxi
        val result = Executable { runBlocking { manageTaxiUseCase.getTaxiByTaxiId(taxiId) } }
        // check
        Assertions.assertThrows(ResourceNotFoundException::class.java, result)
    }

    @Test
    fun `should get correct taxi when id valid`() {
        // given valid id
        val taxiId = FakeGateway.taxes.first().id
        // when get taxi
        val result = runBlocking { manageTaxiUseCase.getTaxiByTaxiId(taxiId) }
        // check
        Assertions.assertEquals(FakeGateway.taxes.first(), result)
    }

    @Test
    fun `should get correct list of taxis when call getAllTaxi with valid page and limit`() {
        // valid page and limit
        val page = 1
        val limit = 5
        // when get taxi
        val result = runBlocking { manageTaxiUseCase.getAllTaxi(page, limit) }
        // then check
        Assertions.assertTrue { result.size <= limit }
    }
    // endregion
}
