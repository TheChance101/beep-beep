package presentation.profile

import domain.entity.Address
import domain.entity.Location
import domain.entity.UserDetails

data class ProfileUIState(
    val user: UserUIState? = UserUIState(),
    val fullName: String = "",
    val phoneNumber: String = "",
    val snackBarMessage: String = "",
    val showSnackBar: Boolean = false,
    val isFullNameError: Boolean = false,
    val isPhoneNumberError: Boolean = false,
    val fullNameErrorMsg: String = "",
    val mobileNumberErrorMsg: String = "",
    val isButtonEnabled: Boolean = false,
)

data class UserUIState(
    val addresses: List<AddressUIState>? = emptyList(),
    val country: String = "",
    val currency: String = "",
    val email: String = "",
    val fullName: String = "",
    val id: String ="",
    val permission: Int = 1,
    val username: String ="",
    val walletBalance: Double =0.0,
    val phoneNumber: String =""
)

data class AddressUIState(
    val address: String = "",
    val id: String = "",
    val location: LocationUIState? = LocationUIState()
)

data class LocationUIState(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)

fun UserDetails.toUIState(): UserUIState {
    return UserUIState(
        addresses = addresses?.map { it.toUIState() },
        country = country ?: "",
        currency = currency ?: "",
        email = email ?: "",
        fullName = fullName ?: "",
        id = id ?: "",
        permission = permission ?: 1,
        username = username ?: "",
        walletBalance = walletBalance ?: 0.0,
        phoneNumber = phoneNumber ?: ""
    )
}

fun Address.toUIState(): AddressUIState {
    return AddressUIState(
        address = address ?: "",
        id = id ?: "",
        location = location?.toUIState()
    )
}

fun Location.toUIState(): LocationUIState {
    return LocationUIState(
        latitude = latitude ?: 0.0,
        longitude = longitude ?: 0.0
    )
}
