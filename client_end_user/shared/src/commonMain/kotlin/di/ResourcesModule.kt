package di

import org.koin.dsl.module
import resources.strings.ar.Arabic
import resources.strings.en.English
import resources.strings.IStringResources

val resourcesModule = module {
    single<IStringResources> { Arabic() }
    single<IStringResources> { English() }
}