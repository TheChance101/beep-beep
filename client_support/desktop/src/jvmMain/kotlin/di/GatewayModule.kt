package di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.thechance.common.data.fake_gateway.ChatFakeGateway
import org.thechance.common.data.gateway.IdentityGateway
import org.thechance.common.domain.gateway.IChatGateway
import org.thechance.common.domain.gateway.IIdentityGateway

val GatewayModule = module {

    singleOf(::IdentityGateway) { bind<IIdentityGateway>() }

    //Fake gateway
//    singleOf(::IdentityFakeGateway) { bind<IIdentityGateway>() }
    singleOf(::ChatFakeGateway) { bind<IChatGateway>() }

}