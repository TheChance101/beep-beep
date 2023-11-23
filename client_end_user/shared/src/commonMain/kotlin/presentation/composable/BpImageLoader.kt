package presentation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme
import com.seiko.imageloader.ImageRequestState
import com.seiko.imageloader.LocalImageLoader
import com.seiko.imageloader.rememberAsyncImagePainter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.composable.modifier.roundedBorderShape
import resources.Resources
import util.generateImageLoader
import util.getPlatformContext

@OptIn(ExperimentalResourceApi::class)
@Composable
fun BpImageLoader(
    imageUrl: String,
    errorPlaceholderImageUrl: String = Resources.images.restaurantErrorPlaceholder,
    modifier: Modifier = Modifier,
    placeHolderModifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
    placeHolderScale:ContentScale = ContentScale.Crop
) {
    val context = getPlatformContext()
    CompositionLocalProvider(LocalImageLoader provides remember { generateImageLoader(context) }) {

        val painter = rememberAsyncImagePainter(imageUrl)

        Box(modifier = modifier){
            when (painter.requestState) {
                is ImageRequestState.Loading -> {
                    Box(Modifier.fillMaxSize().padding(8.dp) , contentAlignment = Alignment.Center){
                        CircularProgressIndicator(color = Theme.colors.primary)
                    }
                }
                is ImageRequestState.Failure -> {
                    Image(
                        modifier = placeHolderModifier.fillMaxSize(),
                        painter = painterResource( errorPlaceholderImageUrl),
                        contentScale = placeHolderScale,
                        contentDescription = contentDescription,
                    )
                }
                is ImageRequestState.Success -> {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painter,
                        contentScale = contentScale,
                        contentDescription = contentDescription,
                    )
                }
            }
        }
    }
}