package di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.thechance.common.data.local.gateway.IdentityGateway
import org.thechance.common.data.local.gateway.LocalGateway
import org.thechance.common.data.remote.fakegateway.TaxisFakeGateway
import org.thechance.common.data.remote.gateway.FakeRemoteGateway
import org.thechance.common.data.remote.gateway.UsersGateway
import org.thechance.common.domain.getway.*

val GatewayModule = module {
    //TODO remove comment when finish testing
//    singleOf(::RemoteGateway) { bind<IRemoteGateway>() }
    singleOf(::LocalGateway) { bind<ILocalGateway>() }
    singleOf(::FakeRemoteGateway) { bind<IRemoteGateway>() }
    singleOf(::IdentityGateway) { bind<IIdentityGateway>() }
    singleOf(::UsersGateway) { bind<IUsersGateway>() }
//    singleOf(::UsersFakeGateway){ bind<IUsersGateway>()}
    singleOf(::TaxisFakeGateway) { bind<ITaxisGateway>() }
//    singleOf(::TaxiGateway) { bind<ITaxiGateway>() }
}