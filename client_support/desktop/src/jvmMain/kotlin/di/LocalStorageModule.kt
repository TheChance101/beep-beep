package di

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module
import org.thechance.common.data.local.model.UserConfigurationCollection

val LocalStorageModule = module {
    single<RealmConfiguration> {
        RealmConfiguration
            .Builder(setOf(UserConfigurationCollection::class))
            .deleteRealmIfMigrationNeeded().build()
    }
    single<Realm> { Realm.open(get<RealmConfiguration>()) }
}