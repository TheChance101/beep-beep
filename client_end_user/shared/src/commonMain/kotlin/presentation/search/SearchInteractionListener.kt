package presentation.search

import presentation.base.BaseInteractionListener

interface SearchInteractionListener : BaseInteractionListener {
    fun onSearchInputChanged(keyword: String)

}
