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
        val effect: E? by screenModel.effect.collectAsState(null)
        val navigator = LocalNavigator.currentOrThrow

        OnRender(state, screenModel)
        effect?.Listen { onEffect(it, navigator) }
    }

    abstract fun onEffect(effect: E, navigator: Navigator)

    @Composable
    abstract fun OnRender(state: S, listener: I)

    @Composable
    private fun E.Listen(function: (E) -> Unit) {
        LaunchedEffect(this) {
            function(this@Listen)
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