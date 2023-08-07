package org.thechance.service_taxi.domain.util

import org.junit.Assert
import org.junit.Test

class ValidationsKtTest {

    @Test
    fun `should return false when id length not equal 24`() {
        // given an invalid id
        val userId = "123456789"
        // when result should return false
        val result = isValidId(userId)
        // then check
        Assert.assertFalse(result)
    }

    @Test
    fun `should return false when id length equal 24 but not in hexString`() {
        // given an invalid id
        val userId = "2a1b3c4d5e6f7a8b9c0d1e2t"
        // when result is return false
        val result = isValidId(userId)
        // then check
        Assert.assertFalse(result)
    }
}