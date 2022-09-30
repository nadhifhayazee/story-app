package com.nadhif.hayazee.domain.home

import androidx.paging.PagingSource
import androidx.paging.PagingState

class BasePagingSource<T : Any>(
    private val data: suspend (Int) -> List<T>?,
    private val maxPage: Int,
) : PagingSource<Int, T>() {
    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {

        val pageIndex = params.key ?: 1

        return try {
            val dataList = data.invoke(pageIndex)
            LoadResult.Page(
                data = dataList.orEmpty(),
                prevKey = if (pageIndex == 1) null else pageIndex - 1,
                nextKey = if (dataList.isNullOrEmpty() || pageIndex == maxPage) null else pageIndex + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }
}