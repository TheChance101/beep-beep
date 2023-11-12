package presentation.main.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.modifier.shimmerEffect

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MainScreenLoadingEffect(isPortrait: Boolean) {
    var rowSize by remember { mutableStateOf(IntSize.Zero) }
    LazyVerticalGrid(
        columns = GridCells.Adaptive(300.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            FlowRow(
                Modifier.fillMaxWidth().onSizeChanged { rowSize = it },
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                val rowWidth = with(LocalDensity.current) { rowSize.width.toDp() }
                val cardSize = if (isPortrait) ((rowWidth / 2) - 4.dp) else 170.dp
                repeat(4) {
                    Box(
                        modifier = Modifier
                            .width(cardSize)
                            .height(160.dp)
                            .padding(vertical = 4.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .shimmerEffect(),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize().padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(16.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .shimmerEffect(),
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .shimmerEffect(),
                            )
                        }
                    }
                }
            }
        }

        item(span = { GridItemSpan(maxCurrentLineSpan) }) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                maxItemsInEachRow = 2
            ) {
                repeat(2) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .shimmerEffect().padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .shimmerEffect(),
                                )
                                Box(
                                    modifier = Modifier
                                        .width(60.dp)
                                        .height(12.dp)
                                        .clip(RoundedCornerShape(2.dp))
                                        .shimmerEffect(),
                                )
                            }
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(60.dp)
                                        .height(12.dp)
                                        .clip(RoundedCornerShape(2.dp))
                                        .shimmerEffect(),
                                )
                                Box(
                                    modifier = Modifier
                                        .width(60.dp)
                                        .height(12.dp)
                                        .clip(RoundedCornerShape(2.dp))
                                        .shimmerEffect(),
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.spacedBy(24.dp),
                            verticalAlignment = Alignment.Bottom
                        ) {
                            repeat(10) {
                                val randomHeight = (50..200).random().dp
                                Box(
                                    modifier = Modifier
                                        .height(randomHeight).width(16.dp)
                                        .clip(RoundedCornerShape(2.dp))
                                        .shimmerEffect()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}