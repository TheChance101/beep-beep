package org.thechance.common.presentation.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.mp.KoinPlatform

interface BaseInteractionListener

@Suppress("BOUNDS_NOT_ALLOWED_IF_BOUNDED_BY_TYPE_PARAMETER")
abstract class BaseScreen<SM, E, S, I>
    : Screen where  I : BaseInteractionListener, SM : BaseScreenModel<S, E>, SM : I {

    @Composable
    protected fun Init(screenModel: SM) {
        val state: S by screenModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        Listen(screenModel.effect){ onEffect(effect = it, navigator = navigator) }
        OnRender(state, screenModel)
    }

    abstract fun onEffect(effect: E, navigator: Navigator)

    @Composable
    abstract fun OnRender(state: S, listener: I)

    @Composable
    private fun Listen(effect: Flow<E>, function: (E) -> Unit, ) {
        LaunchedEffect(Unit) {
            effect.collectLatest {
                it?.let { function(it) }
            }
        }
    }
    @Composable
    protected inline fun <reified T : ScreenModel> getScreenModel(
        qualifier: Qualifier? = null,
        noinline parameters: ParametersDefinition? = null,
    ): T {
        val koin = KoinPlatform.getKoin()
        return rememberScreenModel(tag = qualifier?.value) { koin.get(qualifier, parameters) }
    }
}