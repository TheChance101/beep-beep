package org.thechance.common.presentation.base

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.datetime.Clock
import org.thechance.common.domain.entity.InvalidCredentialsException
import org.thechance.common.domain.entity.NoInternetException
import org.thechance.common.domain.entity.UserNotFoundException
import org.thechance.common.presentation.util.ErrorState

abstract class BaseScreenModel<S, E>(initialState: S) : StateScreenModel<S>(initialState),
    BaseInteractionListener {

    private val _effect = MutableSharedFlow<E?>()
    val effect = _effect.asSharedFlow().throttleFirst(500).mapNotNull { it }

    protected fun <T> tryToExecute(
        callee: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: (ErrorState) -> Unit,
        inScope: CoroutineScope = coroutineScope,
    ): Job {
        return runWithErrorCheck(inScope = inScope, onError = onError) {
            val result = callee()
            onSuccess(result)
        }
    }

    protected fun <T> tryToCollect(
        callee: suspend () -> Flow<T>,
        onNewValue: (T) -> Unit,
        onError: (ErrorState) -> Unit,
        inScope: CoroutineScope = coroutineScope,
    ): Job {
        return runWithErrorCheck(inScope = inScope, onError = onError) {
            callee().collect(onNewValue)
        }
    }

    private fun <T> runWithErrorCheck(
        inScope: CoroutineScope = coroutineScope,
        onError: (ErrorState) -> Unit,
        callee: suspend () -> T,
    ): Job {
        return inScope.launch(Dispatchers.IO) {
            try {
                callee()
            } catch (exception: InvalidCredentialsException) {
                onError(ErrorState.InvalidCredentials(exception.message.toString()))
            } catch (exception: UserNotFoundException) {
                onError(ErrorState.UserNotExist(exception.message.toString()))
            } catch (exception: NoInternetException) {
                onError(ErrorState.NoConnection)
            } catch (exception: Exception) {
                onError(ErrorState.UnKnownError)
            }
        }
    }

    protected fun updateState(updater: (S) -> S) {
        mutableState.update(updater)
    }

    protected fun sendNewEffect(newEffect: E) {
        coroutineScope.launch(Dispatchers.IO) {
            _effect.emit(newEffect)
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