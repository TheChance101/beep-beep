package org.thechance.common.di

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module
import org.thechance.common.data.local.local_dto.TokenLocalDto


val LocalStorageModule = module {
    single<RealmConfiguration> {
        RealmConfiguration
            .Builder(setOf(TokenLocalDto::class))
            .deleteRealmIfMigrationNeeded().build()
    }
    single<Realm> { Realm.open(get<RealmConfiguration>()) }
}