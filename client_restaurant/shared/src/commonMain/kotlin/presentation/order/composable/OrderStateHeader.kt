package presentation.order.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridItemScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.runtime.Composable

@OptIn(ExperimentalFoundationApi::class)
fun LazyStaggeredGridScope.header(content: @Composable LazyStaggeredGridItemScope.() -> Unit) {
    item(span = StaggeredGridItemSpan.FullLine, content = content)
}