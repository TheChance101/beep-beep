package di

import data.local.model.TokenDto
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module

val LocalStorageModule = module {
    single {
        RealmConfiguration.Builder(
            schema = setOf(TokenDto::class)
        ).compactOnLaunch().build()
    }
    single { Realm.open(configuration = get<RealmConfiguration>()) }
}