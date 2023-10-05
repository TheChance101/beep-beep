package di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.thechance.common.data.gateway.local.UserLocalGateway
import org.thechance.common.data.gateway.fake.FakeRemoteGateway
import org.thechance.common.data.gateway.remote.LocationGateway
import org.thechance.common.data.gateway.remote.RestaurantGateway
import org.thechance.common.data.gateway.remote.TaxisGateway
import org.thechance.common.data.gateway.remote.UsersGateway
import org.thechance.common.domain.getway.*

val GatewayModule = module {
    // region Real Gateways
    singleOf(::UserLocalGateway) { bind<IUserLocalGateway>() }
    singleOf(::UsersGateway) { bind<IUsersGateway>() }
    singleOf(::TaxisGateway) { bind<ITaxisGateway>() }
    singleOf(::RestaurantGateway) { bind<IRestaurantGateway>() }
    singleOf(::LocationGateway) { bind<ILocationGateway>() }
    // endregion

    // region Fake Gateways
    singleOf(::FakeRemoteGateway) { bind<IRemoteGateway>() }
//    singleOf(::UsersFakeGateway){ bind<IUsersGateway>()}
//    singleOf(::TaxisFakeGateway) { bind<ITaxisGateway>() }
//    singleOf(::RestaurantFakeGateway) { bind<IRestaurantGateway>() }

    // endregion
}