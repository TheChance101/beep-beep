package data.local.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class RestaurantCollection : RealmObject {
    @PrimaryKey
    var id: String = ""
    var name: String = ""
    var rate: Double = 0.0
    var priceLevel: String = ""
    var imageUrl: String = ""
}