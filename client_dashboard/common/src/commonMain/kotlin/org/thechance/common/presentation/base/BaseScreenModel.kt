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
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.thechance.common.domain.util.*
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
            } catch (exception: MultipleErrorException) {
                exception.errors.toErrorState().let(onError)
            } catch (exception: NoInternetException) {
                onError(ErrorState.NoConnection)
            } catch (exception: UnknownErrorException) {
                println("UnknownErrorException: ${exception.message}")
                onError(ErrorState.UnKnownError)
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

    private fun List<BpError>.toErrorState(): ErrorState.MultipleErrors {
        val errorStates = mutableListOf<ErrorState>()
        forEach { error ->
            val errorState = when (error) {
                is BpError.CuisineNameAlreadyExisted -> ErrorState.CuisineNameAlreadyExisted(error.message)
                is BpError.InvalidCarType -> ErrorState.InvalidCarType(error.message)
                is BpError.InvalidPassword -> ErrorState.InvalidPassword(error.message)
                is BpError.InvalidTaxiColor -> ErrorState.InvalidTaxiColor(error.message)
                is BpError.InvalidTaxiId -> ErrorState.InvalidTaxiId(error.message)
                is BpError.InvalidTaxiPlate -> ErrorState.InvalidTaxiPlate(error.message)
                is BpError.TaxiAlreadyExists -> ErrorState.TaxiAlreadyExists(error.message)
                is BpError.InvalidUserName -> ErrorState.InvalidUserName(error.message)
                is BpError.NoInternetConnection -> ErrorState.NoConnection
                is BpError.NotFoundException -> ErrorState.UnKnownError
                is BpError.PasswordCannotBeBlank -> ErrorState.PasswordCannotBeBlank(error.message)
                is BpError.RestaurantClosed -> ErrorState.RestaurantClosed(error.message)
                is BpError.RestaurantErrorAdd -> ErrorState.RestaurantErrorAdd(error.message)
                is BpError.RestaurantInvalidAddress -> ErrorState.RestaurantInvalidAddress(error.message)
                is BpError.RestaurantInvalidDescription -> ErrorState.RestaurantInvalidDescription(error.message)
                is BpError.RestaurantInvalidId -> ErrorState.RestaurantInvalidId(error.message)
                is BpError.RestaurantInvalidLocation -> ErrorState.RestaurantInvalidLocation(error.message)
                is BpError.RestaurantInvalidName -> ErrorState.RestaurantInvalidName(error.message)
                is BpError.RestaurantInvalidPage -> ErrorState.RestaurantInvalidPage(error.message)
                is BpError.RestaurantInvalidPageLimit -> ErrorState.RestaurantInvalidPageLimit(error.message)
                is BpError.RestaurantInvalidPhone -> ErrorState.RestaurantInvalidPhone(error.message)
                is BpError.RestaurantInvalidRequestParameter ->
                    ErrorState.RestaurantInvalidRequestParameter(error.message)
                is BpError.RestaurantInvalidTime -> ErrorState.RestaurantInvalidTime(error.message)
                is BpError.RestaurantInvalidUpdateParameter ->
                    ErrorState.RestaurantInvalidUpdateParameter(error.message)
                is BpError.RestaurantNotFound -> ErrorState.RestaurantNotFound(error.message)
                is BpError.TaxiNotFound -> ErrorState.TaxiNotFound(error.message)
                is BpError.SeatOutOfRange -> ErrorState.SeatOutOfRange(error.message)
                is BpError.UsernameCannotBeBlank -> ErrorState.UsernameCannotBeBlank(error.message)
                is BpError.InvalidPermission -> ErrorState.InvalidPermission(error.message)
                is BpError.UnknownError -> ErrorState.UnKnownError
            }
            errorStates.add(errorState)
        }
        return ErrorState.MultipleErrors(errorStates)
    }

    protected inline fun <reified T : ErrorState> List<ErrorState>.firstInstanceOfOrNull() =
        filterIsInstance<T>().firstOrNull()

}
