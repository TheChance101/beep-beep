package org.thechance.common.ui.composables.table

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.BpTheme
import com.beepbeep.designSystem.ui.theme.Theme
import java.util.UUID

/**
 * @param rowsCount number of rows in the table without header row
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BpTable(
    rowsCount: Int,
    headers: List<String>,
    modifier: Modifier = Modifier,
    offset: Int = 0,
    firstColumnWeight: Float = 1f,
    lastColumnWeight: Float = 1f,
    otherColumnsWeight: Float = 3f,
    shape: Shape = RoundedCornerShape(8.dp),
    headerTextStyle: TextStyle = Theme.typography.titleMedium.copy(color = Theme.colors.contentTertiary),
    rowPadding: PaddingValues = PaddingValues(16.dp),
    border: Dp = 1.dp,
    headerColor: Color = Theme.colors.surface,
    rowsColor: Color = Theme.colors.background,
    rowContent: @Composable RowScope.(row: Int) -> Unit,
) {
    LazyColumn(
        modifier = modifier.clip(shape = shape).border(border, Theme.colors.contentBorder, shape),
    ) {
        stickyHeader {
            Row(
                Modifier.fillMaxWidth().background(headerColor).padding(rowPadding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                (offset until offset + headers.size).forEach { index ->
                    Text(
                        headers[index - offset],
                        style = headerTextStyle,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(
                            when (index) {
                                offset -> firstColumnWeight
                                offset + headers.size - 1 -> lastColumnWeight
                                else -> otherColumnsWeight
                            }
                        )
                    )
                }
            }
            Divider(Modifier.fillMaxWidth(), thickness = border)
        }

        items(rowsCount) { rowIndex ->
            Row(
                Modifier.fillMaxWidth().background(rowsColor).padding(rowPadding),
                verticalAlignment = Alignment.CenterVertically
            ) { rowContent(rowIndex + offset) }
            Divider(Modifier.fillMaxWidth(), thickness = border)
        }
    }
}

@Preview
@Composable
fun BpTablePreview() {
    var selectedUser by remember { mutableStateOf<String?>(null) }
    BpTheme(useDarkTheme = false) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            val headers = listOf(
                "No.",
                "Users",
                "Username",
                "Email",
                "Country",
                "Permission",
                "",
            )
            val users = getDummyUsers() * 9

            BpTable(
                headers = headers,
                rowsCount = 9,
                offset = 11,
                modifier = Modifier.fillMaxWidth(0.9f),
            ) { index ->
                UserRow(
                    onClickEditUser = { selectedUser = it },
                    user = users[index],
                    position = index + 1,
                )
            }

            LaunchedEffect(selectedUser) {
                if (selectedUser != null) {
                    println("Selected user: $selectedUser")
                }
            }
        }
    }
}

operator fun <E> List<E>.times(i: Int): List<E> {
    return (0 until i).flatMap { this }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RowScope.UserRow(
    onClickEditUser: (userId: String) -> Unit,
    position: Int,
    user: UserRowUIState,
    firstColumnWeight: Float = 1f,
    otherColumnsWeight: Float = 3f,
) {
    Text(
        position.toString(),
        style = Theme.typography.titleMedium.copy(color = Theme.colors.contentTertiary),
        modifier = Modifier.weight(firstColumnWeight),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )

    Row(Modifier.weight(otherColumnsWeight), verticalAlignment = Alignment.CenterVertically) {
        Image(painter = painterResource(user.photoPath), contentDescription = null)
        Text(
            user.fullName,
            modifier = Modifier.padding(start = 16.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }

    Text(
        user.username,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.weight(otherColumnsWeight),
        maxLines = 1,
    )
    Text(
        user.email,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.weight(otherColumnsWeight),
        maxLines = 1,
    )
    Text(
        user.country,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.weight(otherColumnsWeight),
        maxLines = 1,
    )
    FlowRow(
        Modifier.weight(otherColumnsWeight),
        maxItemsInEachRow = 3,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        user.permissions.forEach {
            Icon(
                painter = painterResource(it.iconPath),
                contentDescription = null,
                tint = Theme.colors.contentPrimary.copy(alpha = 0.87f),
                modifier = Modifier.size(24.dp)
            )
        }
    }

    Image(
        painter = painterResource("horizontal_dots.xml"),
        contentDescription = null,
        modifier = Modifier.clickable { onClickEditUser(user.id) }
            .weight(firstColumnWeight)
    )
}

private fun getDummyUsers() = listOf(
    UserRowUIState(
        id = UUID.randomUUID().toString(),
        photoPath = "dummy_img.png",
        fullName = "Ali Mohammed",
        username = "@Ali_ahmed",
        email = "Aliahmed@gmail.com",
        country = "Egypt",
        permissions = listOf(
            UserRowUIState.Permission.ADMIN,
            UserRowUIState.Permission.RESTAURANT,
            UserRowUIState.Permission.SUPPORT,
        ),
    ),
    UserRowUIState(
        id = UUID.randomUUID().toString(),
        photoPath = "dummy_img.png",
        fullName = "Mohammed Sayed",
        username = "@Mohammed_sayed",
        email = "Mosayed@gmail.com",
        country = "Egypt",
        permissions = listOf(
            UserRowUIState.Permission.END_USER,
        ),
    ),
    UserRowUIState(
        id = UUID.randomUUID().toString(),
        photoPath = "dummy_img.png",
        fullName = "Mohammed Sayed",
        username = "@Mohammed_sayed",
        email = "Mosayed@gmail.com",
        country = "Egypt",
        permissions = listOf(
            UserRowUIState.Permission.DRIVER,
            UserRowUIState.Permission.DELIVERY,
        ),
    ),
    UserRowUIState(
        id = UUID.randomUUID().toString(),
        photoPath = "dummy_img.png",
        fullName = "Mohammed Sayed",
        username = "@Mohammed_sayed",
        email = "Mosayed@gmail.com",
        country = "Egypt",
        permissions = listOf(
            UserRowUIState.Permission.END_USER,
        ),
    ),
)

class UserRowUIState(
    val id: String,
    val photoPath: String,
    val fullName: String,
    val username: String,
    val email: String,
    val country: String,
    val permissions: List<Permission>,
) {
    enum class Permission(val iconPath: String) {
        RESTAURANT("outline_restaurants.xml"),
        DRIVER("wheel.xml"),
        END_USER("end_user.xml"),
        SUPPORT("support.xml"),
        DELIVERY("delivery_guy.xml"),
        ADMIN("chart.xml"),
    }
}