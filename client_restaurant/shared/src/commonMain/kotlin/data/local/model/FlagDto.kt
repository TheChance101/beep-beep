package data.local.model

import io.realm.kotlin.types.RealmObject


//TODO choose another correct name "(( and add another flag
class FlagDto:RealmObject {

  var isRememberMeChecked: Boolean = false

}