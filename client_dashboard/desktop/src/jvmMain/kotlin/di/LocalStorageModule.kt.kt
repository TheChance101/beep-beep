package di

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module
import org.thechance.common.data.local.model.ConfigurationCollection


val LocalStorageModule = module {
    single<RealmConfiguration> {
        RealmConfiguration
            .Builder(setOf(ConfigurationCollection::class))
            .deleteRealmIfMigrationNeeded().build()
    }
    single<Realm> { Realm.open(get<RealmConfiguration>()) }
}