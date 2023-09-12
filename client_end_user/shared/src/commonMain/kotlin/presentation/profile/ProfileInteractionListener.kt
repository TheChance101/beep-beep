package presentation.profile

import presentation.base.BaseInteractionListener

interface ProfileInteractionListener :BaseInteractionListener {

  fun  onFullNameChanged(username: String)
  fun  onPhoneNumberChanged(phone: String)
  fun onSaveProfileInfo(fullName: String, phoneNumber: String)

  fun onLogout()
}