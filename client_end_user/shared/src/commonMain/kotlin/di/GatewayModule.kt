package di

import data.gateway.RemoteGateway
import domain.gateway.IRemoteGateway
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val gatewayModule = module {
    singleOf(::RemoteGateway) { bind<IRemoteGateway>() }
}