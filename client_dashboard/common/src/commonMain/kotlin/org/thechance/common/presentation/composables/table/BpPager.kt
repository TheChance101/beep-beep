package org.thechance.common.presentation.composables.table

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.BpIconButton

@Composable
fun BpPager(
    modifier: Modifier = Modifier,
    maxPages: Int,
    currentPage: Int,
    onPageClicked: (Int) -> Unit,
    maxDisplayPages: Int = 6
) {
    val start = calculateStartPage(currentPage, maxPages, maxDisplayPages)
    val end = calculateEndPage(start, currentPage, maxPages, maxDisplayPages)
    val showPages = (start..end).toList()

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ArrowIcon(
            painter = painterResource("left_arrow.svg"),
            onClick = { onPageClicked(currentPage - 1) },
            enable = currentPage != 1
        )

        showPages.forEachIndexed { index, position ->
            BpToggleableTextButton(
                "$position",
                onSelectChange = { onPageClicked(position) },
                selected = currentPage == position
            )
            if (index == showPages.lastIndex) {
                if (position < maxPages - 1) {
                    BpToggleableTextButton(
                        "...",
                        onSelectChange = {},
                        selected = false
                    )
                }
                if (position != maxPages) {
                    BpToggleableTextButton(
                        "$maxPages",
                        onSelectChange = { onPageClicked(maxPages) },
                        selected = currentPage == maxPages
                    )
                }
            }
        }

        ArrowIcon(
            painter = painterResource("right_arrow.svg"),
            onClick = { onPageClicked(currentPage + 1) },
            enable = currentPage != maxPages
        )
    }
}

private fun calculateStartPage(currentPage: Int, maxPages: Int, maxDisplayPages: Int): Int {
    return when {
        maxPages < maxDisplayPages -> 1
        currentPage >= maxPages - 2 -> maxPages - 4
        currentPage >= maxDisplayPages - 2 -> currentPage - 2
        currentPage > maxDisplayPages - 1 -> currentPage - 3
        else -> 1
    }
}

private fun calculateEndPage(
    start: Int, currentPage: Int, maxPages: Int, maxDisplayPages: Int
): Int {
    return if (maxDisplayPages > maxPages) {
        maxPages
    } else if (currentPage <= maxPages - 1) {
        start + 4
    } else {
        maxPages
    }
}
