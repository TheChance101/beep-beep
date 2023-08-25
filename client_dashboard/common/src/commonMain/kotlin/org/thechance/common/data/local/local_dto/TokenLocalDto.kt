package org.thechance.common.data.local.local_dto

import io.realm.kotlin.types.RealmObject

class TokenLocalDto : RealmObject {
    var id = 0
    var token: String = ""
    var type: String = ""
}

enum class TokenType {
    ACCESS_TOKEN,
    REFRESH_TOKEN
}