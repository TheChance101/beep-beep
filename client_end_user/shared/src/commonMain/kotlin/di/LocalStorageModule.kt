package di

import data.local.model.RestaurantCollection
import data.local.model.UserConfigurationCollection
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module

val localStorageModule = module {

    single {
        RealmConfiguration.Builder(
            schema = setOf(
                UserConfigurationCollection::class,
                RestaurantCollection::class
            )
        ).compactOnLaunch().deleteRealmIfMigrationNeeded().build()
    }
    single { Realm.open(configuration = get<RealmConfiguration>()) }
}