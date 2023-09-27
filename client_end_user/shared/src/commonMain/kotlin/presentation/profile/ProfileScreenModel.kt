package presentation.profile

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.UserDetails
import domain.usecase.IManageAuthenticationUseCase
import domain.usecase.IManageUserUseCase
import domain.usecase.validation.IValidationUseCase
import domain.utils.AuthorizationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import presentation.base.BaseScreenModel
import presentation.base.ErrorState


class ProfileScreenModel(
    private val validation: IValidationUseCase,
    private val  manageUser: IManageUserUseCase,
    private val manageAuthentication: IManageAuthenticationUseCase
) :BaseScreenModel<ProfileUIState, ProfileUIEffect >(ProfileUIState()) ,ProfileInteractionListener{

    override val viewModelScope: CoroutineScope = coroutineScope

    init {
        getUserProfile()
        onCheckLogin ()
    }

    private fun onCheckLogin () {
        tryToExecute(
            { manageAuthentication.getAccessToken() } ,
            ::onCheckLoginSuccess,
            ::onCheckLoginError
        )
    }

    private fun onCheckLoginSuccess (accessToken: String) {
        if(accessToken.isNotEmpty()){
            updateState { it.copy(isLogin = true) }
        }
    }
    private fun onCheckLoginError (errorState: ErrorState) {
        updateState { it.copy( isLogin = false)}
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
        updateState { it.copy(user = result ,fullName = result.fullName, phoneNumber = result.phoneNumber ) }
    }

    private fun onGetUserProfileError (errorState: ErrorState) {
        updateState { it.copy( isLogin = false)}
                println(errorState)
    }

    override fun onFullNameChanged(fullName: String) {
        updateState { it.copy(fullName = fullName,isButtonEnabled = true,) }
        checkValidate {
            validation.validateFullName(fullName)
            clearErrors()
        }
    }

    override fun onPhoneNumberChanged(phone: String) {
        updateState { it.copy(phoneNumber = phone,isButtonEnabled = true,) }
        checkValidate {
            validation.validatePhone(phone,state.value.user?.currency!!)
            clearErrors()
        }
    }
    private fun checkValidate(block: () -> Unit) {
        try {
            block()
        } catch (e: AuthorizationException.InvalidFullNameException) {
            updateState { it.copy(isFullNameError = true)
            }
        } catch (e: AuthorizationException.InvalidPhoneException) {
            updateState { it.copy(isPhoneNumberError = true)
            }
        }
    }
    private fun clearErrors() {
        updateState {
            it.copy(
                isFullNameError = false,
                isPhoneNumberError = false
            )
        }
    }
    override fun onSaveProfileInfo() {
        updateState { it.copy(isButtonEnabled = true) }

    }

    override fun onLogout() {
        updateState { it.copy( isLogin = false)}
        viewModelScope.launch {
            manageAuthentication.removeAccessToken()
            manageAuthentication.removeRefreshToken()
        }

    }
    override fun onLogin(){
        sendNewEffect ( ProfileUIEffect.Login )
    }
}