package presentation.base

import cafe.adriel.voyager.core.model.ScreenModel
import domain.utils.InvalidPasswordException
import domain.utils.InvalidUsernameException
import domain.utils.NetworkNotSupportedException
import domain.utils.NoInternetException
import domain.utils.UnAuthorizedException
import domain.utils.WifiDisabledException
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent

abstract class BaseScreenModel<S, E>(initialState: S) : ScreenModel, KoinComponent {

    abstract val viewModelScope: CoroutineScope
    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<E?>()
    val effect = _effect.asSharedFlow().throttleFirst(500).mapNotNull { it }

    protected fun <T> tryToExecute(
        function: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: (ErrorState) -> Unit,
        inScope: CoroutineScope = viewModelScope
    ): Job {
        return runWithErrorCheck(onError, inScope) {
            val result = function()
            onSuccess(result)
        }
    }

    protected fun <T> tryToCollect(
        function: suspend () -> Flow<T>,
        onNewValue: (T) -> Unit,
        onError: (ErrorState) -> Unit,
        inScope: CoroutineScope = viewModelScope
    ): Job {
        return runWithErrorCheck(onError, inScope) {
            function().collect {
                onNewValue(it)
            }
        }
    }

    protected fun updateState(updater: (S) -> S) {
        _state.update(updater)
    }

    protected fun sendNewEffect(newEffect: E) {
        viewModelScope.launch(Dispatchers.IO) {
            _effect.emit(newEffect)
        }
    }

    private fun runWithErrorCheck(
        onError: (ErrorState) -> Unit,
        inScope: CoroutineScope = viewModelScope,
        dispatcher: CoroutineDispatcher = Dispatchers.Unconfined,
        function: suspend () -> Unit
    ): Job {
        return inScope.launch(dispatcher) {
            try {
                function()
            } catch (e: WifiDisabledException) {
                onError(ErrorState.WifiDisabled)
            } catch (e: NoInternetException) {
                onError(ErrorState.NoInternet)
            } catch (e: NetworkNotSupportedException) {
                onError(ErrorState.NetworkNotSupported)
            } catch (e: InvalidUsernameException) {
                onError(ErrorState.InvalidUsername)
            } catch (e: InvalidPasswordException) {
                onError(ErrorState.InvalidPassword)
            } catch (e: UnAuthorizedException) {
                onError(ErrorState.UnAuthorized)
            } catch (e: Exception) {
                onError(ErrorState.RequestFailed)
            }
        }
    }

    private fun <T> Flow<T>.throttleFirst(periodMillis: Long): Flow<T> {
        require(periodMillis > 0)
        return flow {
            var lastTime = 0L
            collect { value ->
                val currentTime = Clock.System.now().toEpochMilliseconds()
                if (currentTime - lastTime >= periodMillis) {
                    lastTime = currentTime
                    emit(value)
                }
            }
        }
    }

}
