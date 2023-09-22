package presentation.base

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import domain.InvalidCredentialsException
import domain.InvalidDriverEmailException
import domain.InvalidDriverNameException
import domain.InvalidPasswordException
import domain.InvalidUserNameException
import domain.NoInternetException
import domain.NotFoundedException
import domain.PermissionDenied
import domain.ServerSideException
import domain.UnAuthorizedException
import domain.UnknownErrorException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent

abstract class BaseScreenModel<S, E>(initialState: S) : ScreenModel, KoinComponent {

    protected val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<E?>()
    val effect = _effect.asSharedFlow().throttleFirst(500)

    protected fun <T> tryToExecute(
        function: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: (ErrorState) -> Unit,
        inScope: CoroutineScope = coroutineScope,
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
        inScope: CoroutineScope = coroutineScope,
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
        coroutineScope.launch(Dispatchers.IO) {
            _effect.emit(newEffect)
        }
    }

    private fun runWithErrorCheck(
        onError: (ErrorState) -> Unit,
        inScope: CoroutineScope = coroutineScope,
        dispatcher: CoroutineDispatcher = Dispatchers.Unconfined,
        function: suspend () -> Unit,
    ): Job {
        return inScope.launch(dispatcher) {
            try {
                function()
            } catch (exception: Exception) {
                when (exception) {
                    is NoInternetException -> onError(ErrorState.NoInternet)
                    is PermissionDenied -> onError(ErrorState.HasNoPermission)
                    is ServerSideException -> onError(ErrorState.ServerError)
                    is UnAuthorizedException -> onError(ErrorState.UnAuthorized)
                    is NotFoundedException -> onError(ErrorState.NotFound(exception.message.toString()))
                    is UnknownErrorException -> onError(
                        ErrorState.UnknownError(
                            exception.message.toString()
                        )
                    )

                    is InvalidCredentialsException -> onError(
                        ErrorState.InvalidCredentials(
                            exception.message.toString()
                        )
                    )

                    is InvalidUserNameException -> onError(ErrorState.InvalidUserName(exception.message.toString()))
                    is InvalidPasswordException -> onError(ErrorState.InvalidPassword(exception.message.toString()))
                    is InvalidDriverNameException -> onError(ErrorState.InvalidDriverName(exception.message.toString()))
                    is InvalidDriverEmailException -> onError(
                        ErrorState.InvalidDriverEmail(
                            exception.message.toString()
                        )
                    )

                    else -> onError(ErrorState.UnknownError(exception.message.toString()))
                }
            }
        }
    }

    private fun <T> Flow<T>.throttleFirst(periodMillis: Long): SharedFlow<T> {
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
        }.shareIn(coroutineScope, SharingStarted.Eagerly)
    }
}
