package com.example.rickmorty.data.remote.paging

import android.util.Log
import androidx.core.net.toUri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickmorty.data.remote.api.CharactersApiService
import com.example.rickmorty.data.remote.dto.Character
import retrofit2.HttpException
import java.io.IOException

class CharacterPagingSource(
    private val apiService: CharactersApiService
) : PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val currentPage = params.key ?: 1

        return try {
            val response = apiService.fetchAllCharacters(currentPage)
            if (!response.isSuccessful) {
                Log.e("CharacterPagingSource", "HTTP error: ${response.code()} - ${response.message()}")
                return LoadResult.Error(HttpException(response))
            }

            val responseBody = response.body()
            if (responseBody == null) {
                Log.e("CharacterPagingSource", "Response body is null")
                return LoadResult.Error(NullPointerException("Response body is null"))
            }

            val nextPageUrl = responseBody.pageInfo.nextPage
            val nextPage = nextPageUrl?.toUri()?.getQueryParameter("page")?.toIntOrNull()

            Log.d("CharacterPagingSource", "Loaded page $currentPage successfully")
            LoadResult.Page(
                data = responseBody.charactersResponseList,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = nextPage
            )
        } catch (e: HttpException) {
            Log.e("CharacterPagingSource", "HTTP error: ${e.message}", e)
            LoadResult.Error(e)
        } catch (e: IOException) {
            Log.e("CharacterPagingSource", "Network error: ${e.message}", e)
            LoadResult.Error(e)
        } catch (e: Exception) {
            Log.e("CharacterPagingSource", "Unexpected error: ${e.message}", e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}