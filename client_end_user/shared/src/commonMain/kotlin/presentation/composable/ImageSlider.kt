package presentation.composable


import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme
import presentation.composable.modifier.noRippleEffect


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageSlider(
    images: List<String>,
    onItemClickListener: (id: Int) -> Unit,
    modifier: Modifier = Modifier,
    indicatorColor: Color = Theme.colors.primary,
    indicatorAlignment: Alignment = Alignment.BottomEnd,
) {
    val pagerState = rememberPagerState()

    Box(
        modifier = modifier.fillMaxWidth().height(160.dp).clip(RoundedCornerShape(8.dp))
            .noRippleEffect { onItemClickListener(pagerState.currentPage) }
    ) {

        HorizontalPager(
            pageCount = images.size,
            state = pagerState,
            contentPadding = PaddingValues(0.dp),
            pageSpacing = 0.dp
        ) { page ->
            BpImageLoader(imageUrl = images[page], contentScale = ContentScale.Crop)
        }

        ImageSliderIndicator(
            itemCount = images.size,
            pagerState = pagerState,
            indicatorColor = indicatorColor,
            indicatorAlignment = indicatorAlignment
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BoxScope.ImageSliderIndicator(
    itemCount: Int,
    pagerState: PagerState,
    indicatorColor: Color,
    indicatorAlignment: Alignment
) {
    val circleOffsetList = remember { mutableStateListOf(Offset(0f, 0f)) }

    val animateOffset by animateOffsetAsState(
        targetValue = circleOffsetList[pagerState.currentPage],
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "indicator"
    )

    Row(
        Modifier.align(indicatorAlignment).padding(vertical = 8.dp, horizontal = 16.dp)
            .drawBehind {
                drawCircle(
                    color = indicatorColor,
                    center = Offset(animateOffset.x + 3.dp.toPx(),size.height / 2),
                    radius = 3.dp.toPx()
                )
            }
    ) {
        repeat(itemCount) { index ->
            Canvas(
                modifier = Modifier.padding(4.dp).size(6.dp)
                    .onGloballyPositioned { coordinates ->
                        val offset = Offset(
                            x = coordinates.positionInParent().x,
                            y = coordinates.positionInParent().y
                        )
                        if (circleOffsetList.size <= index) {
                            circleOffsetList.add(offset)
                        } else {
                            circleOffsetList[index] = offset
                        }
                    },
                onDraw = {
                    drawCircle(color = indicatorColor, style = Stroke(1.dp.toPx()))
                })
        }
    }
}