package domain.usecase

import domain.gateway.FakeGateway


class FakeUseCaseImp(gateway: FakeGateway): FakeUseCase{

}

interface FakeUseCase