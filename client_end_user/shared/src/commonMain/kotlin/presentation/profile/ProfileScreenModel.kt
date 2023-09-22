package presentation.profile

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.UserDetails
import domain.usecase.IManageUserUseCase
import domain.usecase.validation.IValidationUseCase
import domain.utils.AuthorizationException
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.base.ErrorState
import presentation.main.MainScreenUiEffect
import resources.strings.IStringResources

class ProfileScreenModel(
    private val validation: IValidationUseCase,
    private val  manageUser: IManageUserUseCase,
    private val stringResources: IStringResources
) :BaseScreenModel<ProfileUIState, ProfileUIEffect >(ProfileUIState()) ,ProfileInteractionListener{

    override val viewModelScope: CoroutineScope = coroutineScope

    init {
        getUserProfile()
    }

    private fun getUserProfile () {
        tryToExecute(
            { manageUser.getUserProfile() } ,

            ::onGetUserProfileSuccess,
            ::onGetUserProfileError
        )
    }

    private fun onGetUserProfileSuccess (userDetails: UserDetails) {
        val result= userDetails.toUIState()
        updateState { it.copy(user = result, fullName = result.fullName, phoneNumber = result.phoneNumber ) }
    }

    private fun onGetUserProfileError (errorState: ErrorState) {
        println(errorState)
    }

    override fun onFullNameChanged(fullName: String) {
        updateState { it.copy(fullName = fullName,isButtonEnabled = true,) }
        tryCatch {
            validation.validateFullName(fullName)
            clearErrors()
        }
    }

    override fun onPhoneNumberChanged(phone: String) {
        updateState { it.copy(phoneNumber = phone,isButtonEnabled = true,) }
        tryCatch {
            validation.validatePhone(phone)
            clearErrors()
        }
    }
    private fun tryCatch(block: () -> Unit) {
        try {
            block()
        } catch (e: AuthorizationException.InvalidFullNameException) {
            updateState {
                it.copy(
                    fullNameErrorMsg = e.message ?: "Invalid Full Name",
                    isFullNameError = true
                )
            }
        } catch (e: AuthorizationException.InvalidPhoneException) {
            updateState {
                it.copy(
                    mobileNumberErrorMsg = e.message ?: "Invalid Phone Number",
                    isPhoneNumberError = true
                )
            }
        }
    }
    private fun clearErrors() {
        updateState {
            it.copy(
                fullNameErrorMsg = "",
                mobileNumberErrorMsg = "",
                isFullNameError = false,
                isPhoneNumberError = false
            )
        }
    }
    override fun onSaveProfileInfo() {
        updateState { it.copy(isButtonEnabled = true) }

    }

    override fun onLogout() {
        sendNewEffect(ProfileUIEffect.Logout)
    }
}