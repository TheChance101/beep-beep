package di

import data.gateway.local.LocalConfigurationGateway
import kotlinx.coroutines.runBlocking
import org.koin.dsl.module
import util.LocalizationManager

val resourcesModule = module {
    single {
        val localConfigurationGateway = get<LocalConfigurationGateway>()
        val languageCode = runBlocking { localConfigurationGateway.getLanguageCode() }
        LocalizationManager.getStringResources(languageCode)
    }
}

