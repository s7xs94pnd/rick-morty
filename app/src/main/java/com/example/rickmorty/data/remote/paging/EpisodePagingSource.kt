package com.example.rickmorty.data.remote.paging

import android.util.Log
import androidx.core.net.toUri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickmorty.data.remote.api.EpisodesApiService
import com.example.rickmorty.data.remote.dto.Episode
import retrofit2.HttpException
import java.io.IOException

class EpisodePagingSource(
    private val apiService: EpisodesApiService
) : PagingSource<Int, Episode>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Episode> {
        val currentPage = params.key ?: 1

        return try {
            val response = apiService.fetchAllEpisodes(currentPage)
            if (!response.isSuccessful) {
                return LoadResult.Error(HttpException(response))
            }

            val responseBody = response.body()
            if (responseBody == null) {
                return LoadResult.Error(NullPointerException("Response body is null"))
            }

            val nextPageUrl = responseBody.pageInfo.nextPage
            val nextPage = nextPageUrl?.toUri()?.getQueryParameter("page")?.toIntOrNull()

            LoadResult.Page(
                data = responseBody.episodesResponseList ,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = nextPage
            )
        } catch (e: HttpException) {
            Log.e("EpisodePagingSource", "HTTP error: ${e.message}", e)
            LoadResult.Error(e)
        } catch (e: IOException) {
            Log.e("EpisodePagingSource", "Network error: ${e.message}", e)
            LoadResult.Error(e)
        } catch (e: Exception) {
            Log.e("EpisodePagingSource", "Unexpected error: ${e.message}", e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Episode>): Int? {
       return state.anchorPosition?.let { anchorPosition ->
           state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
               ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
       }
    }
}