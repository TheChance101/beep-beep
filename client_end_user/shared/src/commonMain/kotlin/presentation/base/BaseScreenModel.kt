package presentation.base

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import domain.utils.AuthorizationException
import domain.utils.InternetException
import domain.utils.NotFoundException
import domain.utils.RestaurantException
import domain.utils.SocketClosedException
import domain.utils.UnknownErrorException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
        inScope: CoroutineScope = viewModelScope,
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
        inScope: CoroutineScope = viewModelScope,
    ): Job {
        return runWithErrorCheck(onError, inScope) {
            function().distinctUntilChanged().collectLatest {
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
        function: suspend () -> Unit,
    ): Job {
        return inScope.launch(dispatcher) {
            try {
                function()
            } catch (exception: InternetException) {
                handelInternetException(exception, onError)
            } catch (exception: AuthorizationException) {
                handelAuthorizationException(exception, onError)
            } catch (e: AuthorizationException.LocationAccessDeniedException) {
                onError(ErrorState.LocationPermissionDenied)
            } catch (e: RestaurantException) {
                handelRestaurantException(e, onError)
            } catch (e: SocketClosedException) {
                onError(ErrorState.SocketClosed)
            } catch (exception: NotFoundException) {
                onError(ErrorState.RequestFailed)
            } catch (exception: UnknownErrorException) {
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

    protected fun launchDelayed(duration: Long, block: suspend CoroutineScope.() -> Unit): Job {
        return coroutineScope.launch(Dispatchers.IO) {
            delay(duration)
            block()
        }
    }
}
