package org.thechance.common.data.local.local_dto

import io.realm.kotlin.types.RealmObject

class ConfigurationCollection: RealmObject{
    var id: Int = 0
    var keepLoggedIn: Boolean = false
    var accessToken: String = ""
    var refreshToken: String = ""
}