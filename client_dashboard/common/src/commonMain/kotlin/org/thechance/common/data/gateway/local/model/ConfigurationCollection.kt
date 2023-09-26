package org.thechance.common.data.gateway.local.model

import io.realm.kotlin.types.RealmObject

class ConfigurationCollection : RealmObject {
    var id: Int = 0
    var username: String = ""
    var accessToken: String = ""
    var refreshToken: String = ""
    var isDarkMode: Boolean = false
}