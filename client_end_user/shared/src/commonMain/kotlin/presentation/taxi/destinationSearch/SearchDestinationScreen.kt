package presentation.taxi.destinationSearch

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpSimpleTextField
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.base.BaseScreen
import resources.Resources
import util.getStatusBarPadding

class SearchDestinationScreen :
    BaseScreen<SearchDestinationScreenModel, SearchDestinationUiState, SearchDestinationUiEffect, SearchDestinationInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    override fun onEffect(effect: SearchDestinationUiEffect, navigator: Navigator) {

    }

    @OptIn(ExperimentalFoundationApi::class, ExperimentalResourceApi::class)
    @Composable
    override fun onRender(
        state: SearchDestinationUiState,
        listener: SearchDestinationInteractionListener,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colors.background)
                .padding(getStatusBarPadding())
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {

            stickyHeader {
                Column(
                    Modifier.fillMaxWidth().background(Theme.colors.background).padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.Start
                ) {

                    Icon(
                        painterResource(Resources.images.arrowDown),
                        contentDescription = null,
                        modifier = Modifier.padding(8.dp)
                    )

                    Text(
                        text = Resources.strings.searchDestination,
                        style = Theme.typography.title,
                        color = Theme.colors.contentPrimary,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )

                    BpSimpleTextField(
                        state.query,
                        hint = Resources.strings.searchDestination,
                        hintColor = Theme.colors.contentSecondary,
                        onValueChange = listener::onSearchInputChanged,
                        onClick = {},
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}