package org.thechance.common.presentation.base

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.thechance.common.domain.entity.*
import org.thechance.common.presentation.util.ErrorState

abstract class BaseScreenModel<S, E>(initialState: S) : StateScreenModel<S>(initialState),
    BaseInteractionListener {

    private val _effect = MutableSharedFlow<E?>()
    val effect = _effect.asSharedFlow()

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
            } catch (exception: Exception) {
                when(exception){
                    is InvalidCredentialsException -> onError(ErrorState.InvalidCredentials(exception.message.toString()))
                    is UserNotFoundException -> onError(ErrorState.UserNotExist(exception.message.toString()))
                    is NoInternetException -> onError(ErrorState.NoConnection)
                    else -> onError(ErrorState.UnKnownError)
                }
            }
        }
    }

    protected fun updateState(updater: (S) -> S) {
        coroutineScope.launch(Dispatchers.IO) {
            mutableState.update(updater)
        }
    }

    protected fun sendNewEffect(newEffect: E) {
        coroutineScope.launch(Dispatchers.IO) {
            _effect.emit(newEffect)
        }
    }

    protected fun launchDelayed(duration: Long, block: suspend CoroutineScope.() -> Unit): Job {
        return coroutineScope.launch(Dispatchers.IO) {
            delay(duration)
            block()
        }
    }
}
