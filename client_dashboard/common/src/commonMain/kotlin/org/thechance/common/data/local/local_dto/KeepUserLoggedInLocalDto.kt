package org.thechance.common.data.local.local_dto

import io.realm.kotlin.types.RealmObject

class KeepUserLoggedInLocalDto: RealmObject {
    var id: Int = 0
    var keepLoggedIn: Boolean = false
}