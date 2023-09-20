package presentation.preferredRide

import domain.entity.RideQuality
import presentation.base.BaseInteractionListener

interface PreferredRideInteractionListener: BaseInteractionListener {
    fun onClickPreferredRide(quality: RideQuality)
}