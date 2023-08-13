package org.thechance.service_restaurant.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module


@Module(includes = [DataBaseModule::class])
@ComponentScan("org.thechance.service_restaurant")
class AppModule