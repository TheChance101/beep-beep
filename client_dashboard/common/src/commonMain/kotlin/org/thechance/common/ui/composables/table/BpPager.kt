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
) {
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

        repeat(maxPages) {
            val position = it + 1
            BpToggleableTextButton(
                "$position",
                onSelectChange = { onPageClicked(position) },
                selected = currentPage == position
            )
        }

        ArrowIcon(
            painter = painterResource("right_arrow.svg"),
            onClick = { onPageClicked(currentPage + 1) },
            enable = currentPage != maxPages
        )
    }
}