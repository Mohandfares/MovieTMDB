package com.dz.movietmdp.domain.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dz.movietmdp.data.remote.dto.toReviews
import com.dz.movietmdp.domain.model.ReviewItem
import com.dz.movietmdp.domain.repository.MoviesRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ReviewsPagingSource @Inject constructor(
    private val repository: MoviesRepository,
) : PagingSource<Int, ReviewItem>() {

    companion object {
        var movieId: String = ""
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ReviewItem> {
        return try {
            val nextPage = params.key ?: 1
            val reviews = repository.getMovieReviews(movieId, nextPage)
            LoadResult.Page(
                data = reviews.toReviews().results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (reviews.results.isEmpty()) null else reviews.page + 1
            )
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: IOException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ReviewItem>): Int? = state.anchorPosition
}