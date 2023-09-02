package di

import data.gateway.remote.UserGateway
import domain.gateway.IUserGateway
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val gatewayModule = module {
    singleOf(::UserGateway) { bind<IUserGateway>() }
}