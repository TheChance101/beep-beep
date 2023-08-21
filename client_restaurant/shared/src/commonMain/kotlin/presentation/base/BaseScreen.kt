package presentation.base


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
import org.koin.mp.KoinPlatform.getKoin

@Suppress("BOUNDS_NOT_ALLOWED_IF_BOUNDED_BY_TYPE_PARAMETER")
abstract class BaseScreen<VM, S, E, I> : Screen
        where I : BaseInteractionListener, VM : BaseScreenModel<S, E>, VM : I {
    @Composable
    protected fun initScreen(viewModel: VM) {
        val state: S by viewModel.state.collectAsState()
        val effect: E? by viewModel.effect.collectAsState(null)
        val navigator = LocalNavigator.currentOrThrow

        onRender(state, viewModel)
        effect?.Listen {
            onEffect(effect = it, navigator = navigator)
        }
    }

    @Composable
    abstract fun onRender(state: S, listener: I)

    @Composable
    private fun E.Listen(function: (E) -> Unit) {
        LaunchedEffect(this) {
            function(this@Listen)
        }
    }

    abstract fun onEffect(effect: E, navigator: Navigator)

    @Composable
    inline fun <reified T : ScreenModel> getScreenModel(
        qualifier: Qualifier? = null,
        noinline parameters: ParametersDefinition? = null,
    ): T {
        val koin = getKoin()
        return rememberScreenModel(tag = qualifier?.value) { koin.get(qualifier, parameters) }
    }

}