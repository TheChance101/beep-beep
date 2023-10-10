package org.thechance.common.presentation.composables.table

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.modifier.shimmerEffect
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.presentation.resources.Resources
import org.thechance.common.presentation.util.kms

/**
 * @param rowContent number of rows in the table without header row
 */
data class Header(val text: String, val weight: Float = 1f)

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun <T> ColumnScope.BpTable(
    hasConnection: Boolean = true,
    data: List<T>,
    headers: List<Header>,
    modifier: Modifier = Modifier,
    key: ((item: T) -> Any)? = null,
    shape: Shape = RoundedCornerShape(Theme.radius.medium),
    headerTextStyle: TextStyle = Theme.typography.titleMedium.copy(color = Theme.colors.contentTertiary),
    rowPadding: PaddingValues = PaddingValues(16.kms),
    maxHeight: Dp = 72.kms,
    border: Dp = 1.kms,
    borderColor: Color = Theme.colors.contentBorder,
    headerColor: Color = Theme.colors.background,
    rowsColor: Color = Theme.colors.surface,
    isVisible: Boolean = true,
    limitItem: Int = 10,
    isLoading: Boolean = true,
    rowContent: @Composable RowScope.(T) -> Unit,
) {
    Box(modifier = Modifier.weight(1f).padding(bottom = 16.kms)) {
        Column(modifier = Modifier) {
            AnimatedVisibility(visible = isVisible) {
                LazyColumn(modifier = modifier
                    .clip(shape = shape)
                    .border(border, borderColor, shape)
                ) {
                    stickyHeader {
                        TableHeader(headerColor, rowPadding, maxHeight, headers, headerTextStyle,
                            border, borderColor)
                    }


                    if (data.isNotEmpty()) {
                        items(data, key = key) { data ->
                            AnimatedVisibility(!isLoading){
                                TableRow(rowsColor, rowPadding, maxHeight, rowContent, data, border, borderColor)
                            }
                        }
                    }
                        items(limitItem) {
                            AnimatedVisibility(isLoading,
                                enter = fadeIn(animationSpec = tween(1000)),
                                exit = fadeOut(animationSpec = tween(1000))
                            ){
                                TableRowLoading(rowsColor, rowPadding, maxHeight, border, borderColor)
                            }
                        }

                    if (data.isEmpty() && !isLoading) {
                        item {
                            Box(modifier = Modifier.fillParentMaxSize()) {
                                Text(
                                        text = Resources.Strings.noMatchesFound,
                                        modifier = Modifier.align(Alignment.Center).padding(16.kms),
                                        style = Theme.typography.titleMedium,
                                        color = Theme.colors.contentSecondary,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TableRowLoading(
    rowsColor: Color,
    rowPadding: PaddingValues,
    maxHeight: Dp,
    border: Dp,
    borderColor: Color,
) {
    Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(rowsColor)
                .padding(rowPadding)
                .heightIn(max = maxHeight),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.kms)
    ){
        val randomFloat= remember { (50..100).random().dp }
        repeat(9) {
            Box(modifier = Modifier.weight(if (it == 0 || it == 8) 1f else 3f)){
                Box(modifier = Modifier.height(30.kms).width(randomFloat).align(Alignment.CenterStart)
                    .clip(shape = RoundedCornerShape(4.dp)).shimmerEffect(durationMillis = 1500))
            }
        }
    }
    Divider(
            Modifier.fillMaxWidth(),
            thickness = border,
            color = borderColor
    )
}

@Composable
private fun <T> TableRow(
    rowsColor: Color,
    rowPadding: PaddingValues,
    maxHeight: Dp,
    rowContent: @Composable() (RowScope.(T) -> Unit),
    data: T,
    border: Dp,
    borderColor: Color
) {
    Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(rowsColor)
                .padding(rowPadding)
                .heightIn(max = maxHeight),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.kms),
    ) {
        rowContent(data)
    }
    Divider(
            Modifier.fillMaxWidth(),
            thickness = border,
            color = borderColor
    )
}

@Composable
private fun LazyItemScope.TableHeader(
    headerColor: Color,
    rowPadding: PaddingValues,
    maxHeight: Dp,
    headers: List<Header>,
    headerTextStyle: TextStyle,
    border: Dp,
    borderColor: Color
) {
    Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(headerColor)
                .padding(rowPadding)
                .heightIn(max = maxHeight),
            verticalAlignment = Alignment.CenterVertically
    ) {
        headers.forEach { header ->
            Text(
                    header.text,
                    style = headerTextStyle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(header.weight)
            )
        }
    }
    Divider(Modifier.fillMaxWidth(), thickness = border, color = borderColor)
}

