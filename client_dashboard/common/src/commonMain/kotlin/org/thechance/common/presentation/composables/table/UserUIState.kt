package org.thechance.common.ui.composables.table

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme
import java.util.UUID

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


fun getDummyUsers() = listOf(
    UserRowUIState(
        id = UUID.randomUUID().toString(),
        photoPath = "dummy_img.png",
        fullName = "Ali Mohammed1",
        username = "@Ali_ahmed",
        email = "Aliahmed@gmail.com",
        country = "Egypt",
        permissions = listOf(
            UserRowUIState.Permission.ADMIN,
            UserRowUIState.Permission.RESTAURANT,
            UserRowUIState.Permission.SUPPORT,
            UserRowUIState.Permission.DRIVER,
            UserRowUIState.Permission.ADMIN,
        ),
    ),
    UserRowUIState(
        id = UUID.randomUUID().toString(),
        photoPath = "dummy_img.png",
        fullName = "Mohammed Sayed2",
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
        fullName = "Mohammed Sayed3",
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
        fullName = "Mohammed Sayed4",
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
        fullName = "Mohammed Sayed5",
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
        fullName = "Mohammed Sayed6",
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
        fullName = "Mohammed Sayed7",
        username = "@Mohammed_sayed",
        email = "Mosayed@gmail.com",
        country = "Egypt",
        permissions = listOf(
            UserRowUIState.Permission.END_USER,
        ),
    ),
)
