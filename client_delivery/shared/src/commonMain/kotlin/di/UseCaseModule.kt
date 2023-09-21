package di


import domain.usecase.ManageCurrentLocationUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::ManageCurrentLocationUseCase)
}
