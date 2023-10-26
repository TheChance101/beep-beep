package presentation.profile

import presentation.base.BaseInteractionListener

interface ProfileInteractionListener :BaseInteractionListener {
  fun  onFullNameChanged(fullName: String)
  fun  onPhoneNumberChanged(phone: String)
  fun onSaveProfileInfo()
  fun onLogout()
  fun onClickLogin()
}
