package presentation.search

import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel

class SearchScreenModel() :
    BaseScreenModel<SearchUiState, SearchUiEffect>(SearchUiState()),
    SearchInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope


    override fun onSearchInputChanged(keyword: String) {
        TODO("Not yet implemented")
    }

}
