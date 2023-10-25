package data.gateway.remote.pagesource


import app.cash.paging.PagingSource
import app.cash.paging.PagingSourceLoadParams
import app.cash.paging.PagingSourceLoadResult
import app.cash.paging.PagingSourceLoadResultError
import app.cash.paging.PagingSourceLoadResultPage
import app.cash.paging.PagingState
import domain.entity.Meal
import domain.entity.PaginationItems

@Suppress("CAST_NEVER_SUCCEEDS", "USELESS_CAST", "KotlinRedundantDiagnosticSuppress")
abstract class BasePagingSource<Value : Any> : PagingSource<Int, Value>() {

    protected abstract suspend fun fetchData(page: Int, limit: Int): PaginationItems<Value>

    override suspend fun load(params: PagingSourceLoadParams<Int>): PagingSourceLoadResult<Int, Value> {
        val page = params.key ?: 1
        val limit = params.loadSize
        val response = fetchData(page, limit)
       return try {
           PagingSourceLoadResultPage(
               data = response.items,
               prevKey = (page - 1).takeIf { it >= 1 },
               nextKey = if (response.items.isNotEmpty()) page + 1 else null,
           )
        } catch (e: Exception) {
            PagingSourceLoadResultError<Int, Value>(
                Exception("Received a "),
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Value>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

}
