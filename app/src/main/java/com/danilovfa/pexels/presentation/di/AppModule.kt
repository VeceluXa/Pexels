package com.danilovfa.pexels.presentation.di

import com.danilovfa.pexels.domain.repository.PhotoRepository
import com.danilovfa.pexels.presentation.screen.bookmarks.BookmarksViewModel
import com.danilovfa.pexels.presentation.screen.details.DetailsViewModel
import com.danilovfa.pexels.presentation.screen.home.HomeViewModel
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
    fun provideHomeViewModel(photoRepository: PhotoRepository): HomeViewModel =
        HomeViewModel(photoRepository)

    @Provides
    @ViewModelScoped
    fun provideDetailsViewModel(photoRepository: PhotoRepository): DetailsViewModel =
        DetailsViewModel(photoRepository)

    @Provides
    @ViewModelScoped
    fun provideBookmarksViewModel(photoRepository: PhotoRepository): BookmarksViewModel =
        BookmarksViewModel(photoRepository)
}
