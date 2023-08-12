package org.thechance.service_identity.data.geteway.security

import org.thechance.service_identity.data.security.hashing.SaltedHash

/**
 * Created by Aziza Helmy on 8/10/2023.
 */
interface HashingService {
    fun generateSaltedHash(value: String, saltLength: Int = 32): SaltedHash
    fun verify(value: String, saltedHash: SaltedHash): Boolean
}