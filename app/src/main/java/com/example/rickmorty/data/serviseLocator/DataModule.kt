package com.example.rickmorty.data.remote.serviseLocator

import android.content.Context
import androidx.room.Room
import com.example.rickmorty.BuildConfig
import com.example.rickmorty.data.local.dao.AppDatabase
import com.example.rickmorty.data.local.dao.FavoriteCharacterRepository
import com.example.rickmorty.data.remote.api.CharactersApiService
import com.example.rickmorty.data.remote.api.EpisodesApiService
import com.example.rickmorty.data.remote.api.LocationApiService
import com.example.rickmorty.data.remote.repository.CharactersRepository
import com.example.rickmorty.data.remote.repository.EpisodesRepository
import com.example.rickmorty.data.remote.repository.LocationsRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val dataModule = module {
    single { provideOkhttpClient() }
    single { provideRetrofit(get()) }

    single { get<Retrofit>().create(CharactersApiService::class.java) }
    single { get<Retrofit>().create(EpisodesApiService::class.java) }
    single { get<Retrofit>().create(LocationApiService::class.java) }

    single { CharactersRepository(get()) }
    single { EpisodesRepository(get()) }
    single { LocationsRepository(get()) }


    single { provideAppDatabase(androidApplication().applicationContext) }

    single { get<AppDatabase>().favoriteCharacterDao() }

    single { FavoriteCharacterRepository(get()) }

}

fun provideOkhttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}

fun provideAppDatabase(context: Context): AppDatabase {
    return Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "rick_morty_database"
    ).build()
}