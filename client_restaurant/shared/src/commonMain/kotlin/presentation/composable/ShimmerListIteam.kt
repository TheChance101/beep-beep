package presentation.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.modifier.shimmerEffect

@Composable
fun ShimmerItemList(
    isLoading: Boolean ,
    contentAfterLoading : @Composable () -> Unit,
    cardWidth : Dp ,
    cardHeight : Dp,
    modifier: Modifier = Modifier
){
    if (isLoading){
        Column(modifier = modifier){
            Box(
                modifier = Modifier.size(cardWidth,cardHeight).clip(RoundedCornerShape(16.dp)) .shimmerEffect()
            )
            Spacer(modifier.height(8.dp))
            Box(
                modifier = Modifier.size(50.dp,16.dp).shimmerEffect()
            )
            Spacer(modifier.height(8.dp))
            Box(
                modifier = Modifier.size(30.dp,16.dp).shimmerEffect()
            )
        }
    }
    else{
        contentAfterLoading()
    }
}