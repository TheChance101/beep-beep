package data.gateway.remote.pagesource


import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import app.cash.paging.PagingSource
import app.cash.paging.PagingSourceLoadParams
import app.cash.paging.PagingSourceLoadResult
import app.cash.paging.PagingSourceLoadResultError
import app.cash.paging.PagingSourceLoadResultPage
import app.cash.paging.PagingState
import kotlinx.coroutines.flow.Flow

@Suppress("CAST_NEVER_SUCCEEDS", "USELESS_CAST", "KotlinRedundantDiagnosticSuppress")
abstract class BasePagingSource<Value : Any> : PagingSource<Int, Value>() {

    protected abstract suspend fun fetchData(page: Int, limit: Int): List<Value>


    override suspend fun load(params: PagingSourceLoadParams<Int>): PagingSourceLoadResult<Int, Value> {
        val currentPage = params.key ?: 1
        val limit = params.loadSize
        return try {
            val response = fetchData(currentPage, limit)
            val nextKey = (currentPage + 1).takeIf { response.lastIndex >= currentPage }
            println("response: ${response}")
            PagingSourceLoadResultPage(
                data = response,
                prevKey = if (currentPage == 1) null else currentPage - 1,
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
//
//    fun <I : Any, P> getAllWithParameter(
//         parameter:  P,
//         sourceFactory: (P) -> PagingSource<Int, I>,
//     ): Flow<PagingData<I>> {
//         return Pager(
//             config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE),
//             pagingSourceFactory = { sourceFactory(parameter) }
//         ).flow
//     }

    fun <I : Any, P> getAllWithParameters(
        vararg parameters: P,
        sourceFactory: (Array<out P>) -> PagingSource<Int, I>
    ): Flow<PagingData<I>> {
        return Pager(
            config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = { sourceFactory(parameters) }
        ).flow
    }

    companion object {
        private const val DEFAULT_PAGE_SIZE = 30
    }
}
