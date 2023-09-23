package di

import data.local.module.UserConfigurationCollection
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module

val LocalStorageModule = module {
    single {
        RealmConfiguration.Builder(schema = setOf(UserConfigurationCollection::class))
            .compactOnLaunch()
            .deleteRealmIfMigrationNeeded().build()
    }
    single { Realm.open(configuration = get<RealmConfiguration>()) }
}