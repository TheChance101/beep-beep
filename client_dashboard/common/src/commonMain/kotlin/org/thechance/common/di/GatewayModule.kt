package org.thechance.common.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.thechance.common.data.remote.gateway.FakeRemoteGateway
import org.thechance.common.domain.getway.IRemoteGateway


val GatewayModule=  module {
    singleOf(::FakeRemoteGateway){ bind<IRemoteGateway>()}
}