package com.danilovfa.pexels.presentation.di

import com.danilovfa.pexels.domain.repository.BookmarkRepository
import com.danilovfa.pexels.domain.repository.PhotoRepository
import com.danilovfa.pexels.presentation.screen.bookmarks.BookmarksViewModel
import com.danilovfa.pexels.presentation.screen.details.DetailsViewModel
import com.danilovfa.pexels.presentation.screen.home.HomeViewModel
import com.danilovfa.pexels.presentation.screen.root.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    @Provides
    @ViewModelScoped
    fun provideMainViewModel(photoRepository: PhotoRepository): MainViewModel =
        MainViewModel(photoRepository)

    @Provides
    @ViewModelScoped
    fun provideHomeViewModel(photoRepository: PhotoRepository): HomeViewModel =
        HomeViewModel(photoRepository)

    @Provides
    @ViewModelScoped
    fun provideDetailsViewModel(bookmarkRepository: BookmarkRepository): DetailsViewModel =
        DetailsViewModel(bookmarkRepository)

    @Provides
    @ViewModelScoped
    fun provideBookmarksViewModel(bookmarkRepository: BookmarkRepository): BookmarksViewModel =
        BookmarksViewModel(bookmarkRepository)
}
