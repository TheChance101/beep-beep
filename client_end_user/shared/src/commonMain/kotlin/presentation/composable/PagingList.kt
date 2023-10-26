package presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.cash.paging.LoadStateError
import app.cash.paging.LoadStateLoading
import app.cash.paging.LoadStateNotLoading
import app.cash.paging.compose.LazyPagingItems
import com.beepbeep.designSystem.ui.theme.Theme
import presentation.meals.ErrorItem

@Composable
fun <T : Any> PagingList(
    modifier: Modifier = Modifier,
    data: LazyPagingItems<T>,
    errorMessage: String,
    isLoading: Boolean = true,
    content: @Composable (T?) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.colors.background),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(16.dp)
    ) {

        if (data.itemSnapshotList.items.isEmpty()) {
            item {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = errorMessage,
                        modifier = Modifier.fillMaxSize(),
                        textAlign = TextAlign.Center
                    )
                }

            }
        } else {
            items(data.itemCount) { index ->
                val item = data[index]
                content(item)
            }
            if (isLoading) {
                item {
                    CircularProgressIndicator()
                }
            }
        }

        item {
            when (data.loadState.refresh) {
                is LoadStateNotLoading -> Unit
                LoadStateLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier.align(Alignment.Center))
                    }
                }

                is LoadStateError -> {
                    ErrorItem(
                        message = (data.loadState.append as LoadStateError).error.message.toString(),
                        onRefresh = { data.retry() })
                }

                else -> {
                    ErrorItem(
                        message = (data.loadState.append as LoadStateError).error.message.toString(),
                        onRefresh = { data.retry() })
                }
            }
        }


    }
}
