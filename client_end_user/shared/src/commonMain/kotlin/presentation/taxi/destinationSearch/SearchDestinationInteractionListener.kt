package presentation.taxi.destinationSearch

import presentation.base.BaseInteractionListener

interface SearchDestinationInteractionListener : BaseInteractionListener {
    fun onSearchInputChanged(keyword: String)

}
