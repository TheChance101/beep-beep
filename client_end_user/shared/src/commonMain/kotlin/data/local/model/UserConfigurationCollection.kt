package data.local.model

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject

class UserConfigurationCollection : RealmObject {
    var id: Int = 0
    var accessToken: String = ""
    var refreshToken: String = ""
    var isKeepMeLoggedInMeChecked: Boolean = false
    var preferredFood: RealmList<String> = realmListOf()
    var priceLevel: String = ""
    var languageCode: String = ""
    var rideQuality: String = ""
    var isFirstTimeUseApp: Boolean = true
    var isCartEmpty: Boolean = true
}
