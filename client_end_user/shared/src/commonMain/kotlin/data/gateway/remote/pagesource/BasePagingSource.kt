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

    protected abstract suspend fun fetchData(page: Int, limit: Int): PaginationItems<Meal>


    override suspend fun load(params: PagingSourceLoadParams<Int>): PagingSourceLoadResult<Int, Value> {
        val currentPage = params.key ?: 1
        val limit = params.loadSize
        val response = fetchData(currentPage, limit)
        return try {
            val nextKey = (response.page + 1).takeIf { response.items.lastIndex >= response.page }
            println("response: ${response}")
            PagingSourceLoadResultPage(
                data = response.items,
                prevKey = response.page - 1,
                nextKey = nextKey
            ) as PagingSourceLoadResult<Int, Value>
        } catch (e: Exception) {
            PagingSourceLoadResultError<Int, Value>(
                Exception("Received a "),
            ) as PagingSourceLoadResult<Int, Value>
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Value>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

}
