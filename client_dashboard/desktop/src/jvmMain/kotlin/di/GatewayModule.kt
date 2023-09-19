package di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.thechance.common.data.local.gateway.IdentityGateway
import org.thechance.common.data.local.gateway.LocalGateway
import org.thechance.common.data.remote.gateway.*
import org.thechance.common.domain.getway.*

val GatewayModule = module {
    // region Real Gateways
    singleOf(::IdentityGateway) { bind<IIdentityGateway>() }
    singleOf(::UsersGateway) { bind<IUsersGateway>() }
    singleOf(::TaxisGateway) { bind<ITaxisGateway>() }
    singleOf(::RestaurantGateway) { bind<IRestaurantGateway>() }
    singleOf(::LocationGateway) { bind<ILocationGateway>() }
    // endregion

    // region Fake Gateways
    singleOf(::LocalGateway) { bind<ILocalGateway>() }
    singleOf(::FakeRemoteGateway) { bind<IRemoteGateway>() }
//    singleOf(::UsersFakeGateway){ bind<IUsersGateway>()}
//    singleOf(::TaxisFakeGateway) { bind<ITaxisGateway>() }
//    singleOf(::RestaurantFakeGateway) { bind<IRestaurantGateway>() }

    // endregion
}