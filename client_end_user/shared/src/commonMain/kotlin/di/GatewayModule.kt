package di

import data.remote.RemoteGateway
import domain.gateway.IRemoteGateway
import org.koin.dsl.module

val gatewayModule = module {

    single<IRemoteGateway> { RemoteGateway(get()) }

}