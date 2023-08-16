package org.thechance.common.ui.composables.table

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun BpPager(
    modifier: Modifier = Modifier,
    maxPages: Int,
    currentPage: Int,
    onPageClicked: (Int) -> Unit,
    maxDisplayPages: Int = 6
) {
    val pagerTimesToRepeat = if (maxPages > maxDisplayPages) {
        maxDisplayPages - 1
    } else {
        maxPages - 1
    }
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

        repeat(pagerTimesToRepeat) {
            val position = it + 1
            BpToggleableTextButton(
                "$position",
                onSelectChange = { onPageClicked(position) },
                selected = currentPage == position
            )
        }

        if (maxDisplayPages < maxPages) {
            BpToggleableTextButton(
                "...",
                onSelectChange = {},
                selected = false
            )
        }

        BpToggleableTextButton(
            "$maxPages",
            onSelectChange = { onPageClicked(maxPages) },
            selected = currentPage == maxPages
        )

        ArrowIcon(
            painter = painterResource("right_arrow.svg"),
            onClick = { onPageClicked(currentPage + 1) },
            enable = currentPage != maxPages
        )
    }
}