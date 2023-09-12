package presentation.preferredRide

import presentation.base.BaseInteractionListener

interface PreferredRideInteractionListener: BaseInteractionListener {
    fun onClickPreferredRide(flag: Int)
}