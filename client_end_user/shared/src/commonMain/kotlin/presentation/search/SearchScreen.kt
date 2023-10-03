package presentation.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpSimpleTextField
import com.beepbeep.designSystem.ui.theme.Theme
import domain.entity.Restaurant
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.base.BaseScreen
import resources.Resources
import util.getStatusBarPadding

class SearchScreen :
    BaseScreen<SearchScreenModel, SearchUiState, SearchUiEffect, SearchInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    @OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
    @Composable
    override fun onRender(state: SearchUiState, listener: SearchInteractionListener) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colors.background)
                .padding(getStatusBarPadding())
                .padding(top = 16.dp)
        ) {

            stickyHeader {
                BpSimpleTextField(
                    state.query,
                    hint = Resources.strings.searchHint,
                    hintColor = Theme.colors.contentSecondary,
                    onValueChange = listener::onSearchInputChanged,
                    onClick = {},
                    leadingPainter = painterResource(Resources.images.searchOutlined),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            item {
                LazyRow {
                    items(state.restaurants) {
                        Text(it.name, color = Color.White)
                    }
                }
            }

            items(state.meals) {
                Text(it.name, color = Color.White)
            }
        }
    }

    override fun onEffect(effect: SearchUiEffect, navigator: Navigator) {

    }




}
