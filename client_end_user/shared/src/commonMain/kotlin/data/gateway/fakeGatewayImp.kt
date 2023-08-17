package data.gateway

import domain.gateway.FakeGateway
import domain.source.FakeLocalDataSource
import domain.source.FakeRemoteDataSource

class FakeGatewayImp(
    localDataSource: FakeLocalDataSource,
    remoteDataSource: FakeRemoteDataSource
) : FakeGateway {

}