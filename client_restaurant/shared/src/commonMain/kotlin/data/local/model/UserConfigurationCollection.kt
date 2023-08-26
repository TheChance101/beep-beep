package data.local.model

import io.realm.kotlin.types.RealmObject

class UserConfigurationCollection : RealmObject {
    var accessToken: String = ""
    var refreshToken: String = ""
    var isKeepMeLoggedInMeChecked: Boolean = false
}