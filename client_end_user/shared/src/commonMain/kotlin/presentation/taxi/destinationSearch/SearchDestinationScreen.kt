package presentation.taxi.destinationSearch

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpSimpleTextField
import com.beepbeep.designSystem.ui.theme.BpTheme
import com.beepbeep.designSystem.ui.theme.Theme
import com.seiko.imageloader.rememberAsyncImagePainter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.auth.login.LoginScreen
import presentation.base.BaseScreen
import presentation.composable.BottomSheet
import presentation.composable.MealBottomSheet
import presentation.composable.MealCard
import presentation.composable.ModalBottomSheetState
import presentation.composable.modifier.noRippleEffect
import presentation.composable.modifier.roundedBorderShape
import presentation.resturantDetails.Composable.NeedToLoginSheet
import presentation.resturantDetails.Composable.ToastMessage
import presentation.resturantDetails.RestaurantScreen
import resources.Resources
import util.getStatusBarPadding
import util.root

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
