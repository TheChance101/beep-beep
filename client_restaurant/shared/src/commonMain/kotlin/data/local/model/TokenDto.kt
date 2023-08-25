package data.local.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class TokenDto : RealmObject {
    @PrimaryKey
    private var _id: ObjectId = ObjectId()
    var accessToken: String = ""
    var refreshToken: String = ""
}