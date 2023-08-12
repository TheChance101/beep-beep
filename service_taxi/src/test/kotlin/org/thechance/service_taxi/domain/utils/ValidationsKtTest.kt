package org.thechance.service_taxi.domain.utils

import org.junit.Assert
import org.junit.Test
import org.thechance.service_taxi.domain.usecase.utils.Validations

class ValidationsKtTest {
    private val validations = Validations()

    // region id
    @Test
    fun `should return false when id length not equals 24`() {
        // given an invalid id
        val userId = "123456789"
        // when result is false
        val result = validations.isValidId(userId)
        // then check
        Assert.assertFalse(result)
    }

    @Test
    fun `should return false when id length equals 24 but not in hexString`() {
        // given an invalid id
        val userId = "2a1b3c4d5e6f7a8b9c0d1e2t"
        // when result is false
        val result = validations.isValidId(userId)
        // then check
        Assert.assertFalse(result)
    }

    @Test
    fun `should return true when id length equals 24 and in hexString`() {
        // given a valid id
        val userId = "2a1b3c4d5e6f7a8b9c0d1e2f"
        // when result is true
        val result = validations.isValidId(userId)
        // then check
        Assert.assertTrue(result)
    }
    // endregion

    // region price
    @Test
    fun `should return false when price les than 10`() {
        // given an invalid price
        val price = 9.0
        // when result is false
        val result = validations.isValidPrice(price)
        // then check
        Assert.assertFalse(result)
    }

    @Test
    fun `should return true when price equal 10`() {
        // given an valid price
        val price = 10.0
        // when result is true
        val result = validations.isValidPrice(price)
        // then check
        Assert.assertTrue(result)
    }

    @Test
    fun `should return true when price greater than 10`() {
        // given an valid price
        val price = 15.0
        // when result is true
        val result = validations.isValidPrice(price)
        // then check
        Assert.assertTrue(result)
    }
    // endregion

    // region location
    @Test
    fun `should return true when valid location`() {
        // given an valid location
        val latitude = 80.0
        val longitude = 150.0
        // when result is true
        val result = validations.isValidLocation(latitude, longitude)
        // then check
        Assert.assertTrue(result)
    }

    @Test
    fun `should return false when invalid location`() {
        // given an invalid location
        val latitude = 200.0
        val longitude = 190.0
        // when result is true
        val result = validations.isValidLocation(latitude, longitude)
        // then check
        Assert.assertFalse(result)
    }
    // endregion

    // region plate number
    @Test
    fun `should return true when valid plate form Egypt`() {
        // given an valid plate
        val plate = "1234 MOS"
        // when result is true
        val result = validations.isisValidPlateNumber(plate)
        // then check
        Assert.assertTrue(result)
    }

    @Test
    fun `should return true when valid plate form Saudi Arabia`() {
        // given an valid plate
        val plate = "123456 MS"
        // when result is true
        val result = validations.isisValidPlateNumber(plate)
        // then check
        Assert.assertTrue(result)
    }

    @Test
    fun `should return false when invalid plate`() {
        // given an valid plate
        val plate = "123456adsMS"
        // when result is true
        val result = validations.isisValidPlateNumber(plate)
        // then check
        Assert.assertFalse(result)
    }
    // endregion
}