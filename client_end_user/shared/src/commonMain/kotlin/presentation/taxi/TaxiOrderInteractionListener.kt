package presentation.taxi

import presentation.base.BaseInteractionListener

interface TaxiOrderInteractionListener : BaseInteractionListener {
    fun onBackButtonClicked()
    fun onSearchDestination()
    fun onSearchInputChanged(keyword: String)

}
