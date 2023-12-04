package presentation.profile

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.User
import domain.usecase.IManageAuthenticationUseCase
import domain.usecase.IManageProfileUseCase
import domain.usecase.validation.IValidationUseCase
import domain.utils.AuthorizationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class ProfileScreenModel(
    private val validation: IValidationUseCase,
    private val manageProfile: IManageProfileUseCase,
    private val manageAuthentication: IManageAuthenticationUseCase
) : BaseScreenModel<ProfileUIState, ProfileUIEffect>(ProfileUIState()), ProfileInteractionListener {

    override val viewModelScope: CoroutineScope = coroutineScope

    init {
        checkIfLoggedIn()
    }

    private fun checkIfLoggedIn() {
        tryToExecute(
            { manageAuthentication.getAccessToken() },
            ::onCheckIfLoggedInSuccess,
            ::onLogoutError
        )
    }

    private fun getUserProfile() {
        tryToExecute(
            { manageProfile.getUserProfile() },
            ::onGetUserProfileSuccess,
            ::onGetUserProfileError
        )
    }

    private fun onGetUserProfileSuccess(userDetails: User) {
        val result = userDetails.toUIState()
        updateState {
            it.copy(
                user = result,
                fullName = result.fullName,
                phoneNumber = result.phoneNumber,
                isButtonEnabled = false,
                isLoading = false,
                isLoggedIn = true,
            )
        }
    }

    private fun onGetUserProfileError(errorState: ErrorState) {
        println("Error when get user profile: $errorState")
    }

    private fun onCheckIfLoggedInSuccess(accessToken: Flow<String>) {
        coroutineScope.launch {
            accessToken.distinctUntilChanged().collectLatest { token ->
                if (token.isNotEmpty()) {
                    updateState { it.copy(isLoggedIn = true) }
                    getUserProfile()
                } else {
                    updateState { it.copy(isLoggedIn = false) }
                }
            }
        }
    }

    private fun onLogoutError(errorState: ErrorState) {
        updateState { it.copy(isLoggedIn = false) }
    }

    override fun onFullNameChanged(fullName: String) {
        updateState { it.copy(fullName = fullName) }
        checkValidate {
            validation.validateFullName(fullName)
            clearErrors()
        }
    }

    override fun onPhoneNumberChanged(phone: String) {
        updateState { it.copy(phoneNumber = phone) }
        checkValidate {
            validation.validatePhone(phone, state.value.user.currency)
            clearErrors()
        }
    }

    override fun onSaveProfileInfo() {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            { manageProfile.updateUserProfile(state.value.fullName, state.value.phoneNumber) },
            ::onGetUserProfileSuccess,
            ::onUpdateProfileError
        )
    }

    private fun onUpdateProfileError(errorState: ErrorState) {
        updateState { it.copy(isLoading = false) }
    }

    override fun onLogout() {
        tryToExecute(
            { manageAuthentication.logout() },
            { onLogoutSuccess() },
            ::onLogoutError
        )
    }

    private fun onLogoutSuccess() {
        updateState { it.copy(isLoggedIn = false) }
    }

    override fun onClickLogin() {
        sendNewEffect(ProfileUIEffect.NavigateToLoginScreen)
    }

    private fun clearErrors() {
        updateState {
            it.copy(
                isFullNameError = false,
                isPhoneNumberError = false,
                isButtonEnabled = it.isNameOrPhoneChange()
            )
        }
    }

    private fun checkValidate(block: () -> Unit) {
        try {
            block()
        } catch (e: AuthorizationException.InvalidFullNameException) {
            updateState {
                it.copy(isFullNameError = true, isButtonEnabled = false)
            }
        } catch (e: AuthorizationException.InvalidPhoneException) {
            updateState {
                it.copy(isPhoneNumberError = true, isButtonEnabled = false)
            }
        }
    }
}
