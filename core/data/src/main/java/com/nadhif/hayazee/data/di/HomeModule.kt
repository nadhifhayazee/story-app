package com.nadhif.hayazee.data.di

import com.nadhif.hayazee.data.home.StoryRepository
import com.nadhif.hayazee.data.home.StoryServiceRepositoryImpl
import com.nadhif.hayazee.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Provides
    fun provideHomeServiceRepository(
        apiService: ApiService
    ): StoryRepository {
        return StoryServiceRepositoryImpl(apiService)
    }
}