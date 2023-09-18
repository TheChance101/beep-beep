package di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.thechance.common.data.local.gateway.UserLocalGateway
import org.thechance.common.data.remote.fakegateway.FakeRemoteGateway
import org.thechance.common.data.remote.gateway.RestaurantGateway
import org.thechance.common.data.remote.gateway.TaxisGateway
import org.thechance.common.data.remote.gateway.UsersGateway
import org.thechance.common.domain.getway.*

val GatewayModule = module {
    // region Real Gateways
    singleOf(::UserLocalGateway) { bind<IUserLocalGateway>() }
    singleOf(::UsersGateway) { bind<IUsersGateway>() }
    singleOf(::TaxisGateway) { bind<ITaxisGateway>() }
    singleOf(::RestaurantGateway){ bind<IRestaurantGateway>() }
    // endregion

    // region Fake Gateways
    singleOf(::FakeRemoteGateway) { bind<IRemoteGateway>() }
//    singleOf(::UsersFakeGateway){ bind<IUsersGateway>()}
//    singleOf(::TaxisFakeGateway) { bind<ITaxisGateway>() }
//    singleOf(::RestaurantFakeGateway) { bind<IRestaurantGateway>() }

    // endregion
}