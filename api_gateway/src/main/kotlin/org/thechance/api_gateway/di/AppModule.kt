package org.thechance.api_gateway.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module


@Module(includes = [ApiClientModule::class])
@ComponentScan("org.thechance.api_gateway")
class AppModule