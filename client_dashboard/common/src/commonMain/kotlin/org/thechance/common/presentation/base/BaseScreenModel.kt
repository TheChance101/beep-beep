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
            } catch (exception: TaxiException) {
                handleTaxiException(exception, onError)
            } catch (exception: RestaurantException) {
                handleRestaurantException(exception, onError)
            } catch (exception: IdentityException) {
                handleIdentityException(exception, onError)
            } catch (exception: NoInternetException) {
                onError(ErrorState.NoConnection)
            }catch (exception: UnknownErrorException) {
                println("UnknownErrorException: ${exception.message}")
                onError(ErrorState.UnKnownError)
            } catch (exception: Exception) {
                onError(ErrorState.UnKnownError)
            }
        }
    }

    private inline fun handleIdentityException(
        exception: IdentityException,
        onError: (ErrorState) -> Unit
    ) {
        when (exception) {
            is InvalidPasswordException -> onError(ErrorState.InvalidPassword(exception.message.toString()))
            is InvalidUserNameException -> onError(ErrorState.UserNotExist(exception.message.toString()))
            is UsernameCannotBeBlankException -> onError(ErrorState.UsernameCannotBeBlank(exception.message.toString()))
            is InvalidUserRequestParameterException -> onError(
                ErrorState.InvalidUserRequestParameter(
                    exception.message.toString()
                )
            )
        }
    }

   private inline fun handleRestaurantException(
        exception: RestaurantException,
        onError: (ErrorState) -> Unit
    ) {
        when (exception) {
            is RestaurantInvalidIdException -> onError(ErrorState.RestaurantInvalidId(exception.message.toString()))
            is RestaurantInvalidNameException -> onError(ErrorState.RestaurantInvalidName(exception.message.toString()))
            is RestaurantInvalidLocationException -> onError(
                ErrorState.RestaurantInvalidLocation(
                    exception.message.toString()
                )
            )

            is RestaurantInvalidDescriptionException -> onError(
                ErrorState.RestaurantInvalidDescription(
                    exception.message.toString()
                )
            )

            is RestaurantInvalidPhoneException -> onError(
                ErrorState.RestaurantInvalidPhone(
                    exception.message.toString()
                )
            )

            is RestaurantInvalidTimeException -> onError(ErrorState.RestaurantInvalidTime(exception.message.toString()))
            is RestaurantInvalidPageException -> onError(ErrorState.RestaurantInvalidPage(exception.message.toString()))
            is RestaurantInvalidPageLimitException -> onError(
                ErrorState.RestaurantInvalidPageLimit(
                    exception.message.toString()
                )
            )

            is RestaurantInvalidUpdateParameterException -> onError(
                ErrorState.RestaurantInvalidUpdateParameter(
                    exception.message.toString()
                )
            )

            is RestaurantInvalidAddressException -> onError(
                ErrorState.RestaurantInvalidAddress(
                    exception.message.toString()
                )
            )

            is RestaurantInvalidRequestParameterException -> onError(
                ErrorState.RestaurantInvalidRequestParameter(
                    exception.message.toString()
                )
            )

            is RestaurantNotFoundException -> onError(ErrorState.RestaurantNotFound(exception.message.toString()))
            is RestaurantErrorAddException -> onError(ErrorState.RestaurantErrorAdd(exception.message.toString()))
            is RestaurantClosedException -> onError(ErrorState.RestaurantClosed(exception.message.toString()))
            is CuisineNameAlreadyExistedException -> onError(
                ErrorState.CuisineNameAlreadyExisted(
                    exception.message.toString()
                )
            )
        }
    }

   private inline fun handleTaxiException(
        exception: TaxiException,
        onError: (ErrorState) -> Unit
    ) {
        when (exception) {
            is TaxiAlreadyExistsException -> { onError(ErrorState.TaxiAlreadyExists(exception.message.toString())) }
            is InvalidTaxiRequestParameterException -> { onError(ErrorState.InvalidTaxiRequestParameter(exception.message.toString())) }
            is InvalidTaxiIdException -> { onError(ErrorState.InvalidTaxiId(exception.message.toString())) }
            is InvalidTaxiColorException -> { onError(ErrorState.InvalidTaxiColor(exception.message.toString())) }
            is TaxiNotFoundException -> { onError(ErrorState.TaxiNotFound(exception.message.toString())) }
            is InvalidTaxiPlateException -> { onError(ErrorState.InvalidTaxiPlate(exception.message.toString())) }
            is InvalidCarTypeException -> { onError(ErrorState.InvalidCarType(exception.message.toString())) }
            is SeatOutOfRangeException -> { onError(ErrorState.SeatOutOfRange(exception.message.toString())) }
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
