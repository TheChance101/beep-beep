package org.thechance.common.data.local.model

import io.realm.kotlin.types.RealmObject


class UserConfigurationCollection : RealmObject{
    var id: Int = 0
    var keepLoggedIn: Boolean = false
    var accessToken: String = ""
    var refreshToken: String = ""
}