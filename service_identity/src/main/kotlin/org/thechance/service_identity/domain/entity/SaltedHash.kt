package org.thechance.service_identity.domain.entity

/**
 * Created by Aziza Helmy on 8/10/2023.
 */
data class SaltedHash(val hash: String, val salt: String)