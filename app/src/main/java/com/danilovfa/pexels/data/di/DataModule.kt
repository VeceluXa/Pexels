package com.danilovfa.pexels.data.di

import android.content.Context
import androidx.room.Room
import com.danilovfa.pexels.data.local.PexelsDao
import com.danilovfa.pexels.data.local.PexelsDatabase
import com.danilovfa.pexels.data.remote.PexelsApi
import com.danilovfa.pexels.data.remote.interceptor.CachingInterceptor
import com.danilovfa.pexels.data.remote.interceptor.PexelsInterceptor
import com.danilovfa.pexels.data.remote.interceptor.loggingInterceptor
import com.danilovfa.pexels.data.repository.BookmarkRepositoryImpl
import com.danilovfa.pexels.data.repository.PhotoRepositoryImpl
import com.danilovfa.pexels.domain.repository.BookmarkRepository
import com.danilovfa.pexels.domain.repository.PhotoRepository
import com.danilovfa.pexels.utils.Constants.BASE_URL
import com.danilovfa.pexels.utils.Constants.RETROFIT_CACHE_FOLDER
import com.danilovfa.pexels.utils.Constants.RETROFIT_CACHE_SIZE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun providePexelsInterceptor(): PexelsInterceptor = PexelsInterceptor()

    @Provides
    @Singleton
    fun provideCachingInterceptor(@ApplicationContext context: Context): CachingInterceptor =
        CachingInterceptor(context)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        pexelsInterceptor: PexelsInterceptor,
        cachingInterceptor: CachingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .cache(Cache(context.cacheDir.resolve(RETROFIT_CACHE_FOLDER), RETROFIT_CACHE_SIZE))
            .addInterceptor(cachingInterceptor)
            .addInterceptor(pexelsInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun providePexelsApi(retrofit: Retrofit): PexelsApi = retrofit.create(PexelsApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PexelsDatabase =
        Room.databaseBuilder(
            context,
            PexelsDatabase::class.java,
            PexelsDatabase.NAME
        ).build()

    @Provides
    @Singleton
    fun providePexelsDao(pexelsDatabase: PexelsDatabase): PexelsDao = pexelsDatabase.dao

    @Provides
    @Singleton
    fun providePhotoRepository(pexelsApi: PexelsApi): PhotoRepository =
        PhotoRepositoryImpl(pexelsApi = pexelsApi)

    @Provides
    @Singleton
    fun provideBookmarkRepository(pexelsDao: PexelsDao): BookmarkRepository =
        BookmarkRepositoryImpl(pexelsDao = pexelsDao)
}
