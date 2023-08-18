package org.thechance.common.presentation.composables.table

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.BpTheme
import com.beepbeep.designSystem.ui.theme.Theme

/**
 * @param rowsCount number of rows in the table without header row
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> BpTable(
    data: List<T>,
    headers: List<Header>,
    modifier: Modifier = Modifier,
    key: ((item: T) -> Any)? = null,
    rowsCount: Int = 9,
    offset: Int = 0,
    shape: Shape = RoundedCornerShape(Theme.radius.medium),
    headerTextStyle: TextStyle = Theme.typography.titleMedium.copy(color = Theme.colors.contentTertiary),
    rowPadding: PaddingValues = PaddingValues(16.dp),
    maxHeight: Dp = 72.dp,
    border: Dp = 1.dp,
    borderColor: Color = Theme.colors.contentBorder,
    headerColor: Color = Theme.colors.background,
    rowsColor: Color = Theme.colors.surface,
    rowContent: @Composable RowScope.(T) -> Unit,
) {
    LazyColumn(
        modifier = modifier.clip(shape = shape).border(border, borderColor, shape),
    ) {
        stickyHeader {
            Row(
                Modifier.fillMaxWidth().background(headerColor).padding(rowPadding)
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

        val paginatedData =
            data.filterIndexed { index, _ -> index in (offset * rowsCount) until (offset * rowsCount) + rowsCount }
        items(paginatedData, key = key) {
            Row(
                Modifier.fillMaxWidth().background(rowsColor).padding(rowPadding)
                    .heightIn(max = maxHeight),
                verticalAlignment = Alignment.CenterVertically
            ) { rowContent(it) }
            Divider(Modifier.fillMaxWidth(), thickness = border, color = borderColor)
        }
    }
}

data class Header(val text: String, val weight: Float = 1f)

@Preview
@Composable
fun BpTablePreview() {
    var selectedUser by remember { mutableStateOf<String?>(null) }
    var selectedPage by remember { mutableStateOf(1) }
    val pageCount = 2

    BpTheme(useDarkTheme = false) {
        Column(
            Modifier.fillMaxSize().padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Theme.dimens.space16),
        ) {
            val headers = listOf(
                Header("No.", 1f),
                Header("Users", 3f),
                Header("Username", 3f),
                Header("Email", 3f),
                Header("Country", 3f),
                Header("Permission", 3f),
                Header("", 1f),
            )
            val users = getDummyUsers()

            BpTable(
                data = users,
                key = { it.id },
                headers = headers,
                modifier = Modifier.fillMaxWidth(),
                rowsCount = pageCount,
                offset = selectedPage - 1,
                rowContent = { user ->
                    UserRow(
                        onClickEditUser = { selectedUser = it },
                        user = user,
                        position = users.indexOf(user) + 1,
                    )
                },
            )


            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TotalItemsIndicator(
                    numberItemInPage = 50,
                    totalItems = 190,
                    itemType = "user",
                    onItemPerPageChange = {}
                )

                BpPager(
                    maxPages = pageCount,
                    currentPage = selectedPage,
                    onPageClicked = { selectedPage = it },
                )
            }
        }
    }
}

