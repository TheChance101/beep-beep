package data.gateway.remote.pagesource


import app.cash.paging.PagingSource
import app.cash.paging.PagingSourceLoadParams
import app.cash.paging.PagingSourceLoadResult
import app.cash.paging.PagingSourceLoadResultError
import app.cash.paging.PagingSourceLoadResultPage
import app.cash.paging.PagingState
import domain.entity.PaginationItems
import domain.utils.GeneralException
import kotlinx.coroutines.delay

@Suppress("CAST_NEVER_SUCCEEDS", "USELESS_CAST", "KotlinRedundantDiagnosticSuppress")
abstract class BasePagingSource<Value : Any> : PagingSource<Int, Value>() {

    protected abstract suspend fun fetchData(page: Int, limit: Int): PaginationItems<Value>

    override suspend fun load(params: PagingSourceLoadParams<Int>): PagingSourceLoadResult<Int, Value> {
        val currentPage = params.key ?: 1
        val limit = 10
        val response = fetchData(currentPage, limit)
        val endOfPaginationReached = response.items.isEmpty()
        return try {
            if (response.items.isNotEmpty()) {
                PagingSourceLoadResultPage(
                    data = response.items,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (endOfPaginationReached) null else currentPage + 1
                )
            } else {
                PagingSourceLoadResultPage(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: GeneralException.UnknownErrorException) {
            PagingSourceLoadResultError<Int, Value>(
                GeneralException.UnknownErrorException,
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Value>): Int? {
        return state.anchorPosition
    }

}
