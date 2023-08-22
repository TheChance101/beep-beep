package org.thechance.common.presentation.util

sealed interface ErrorState {
    object NoConnection : ErrorState
    object UnKnownError : ErrorState
}