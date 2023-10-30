package presentation.taxi

import presentation.base.BaseInteractionListener

interface TaxiOrderInteractionListener : BaseInteractionListener {
    fun onBackButtonClicked()
    fun onSearchInputChanged(keyword: String)

}
