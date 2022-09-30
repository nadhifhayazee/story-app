package com.nadhif.hayazee.data.di

import com.nadhif.hayazee.data.home.HomeRepository
import com.nadhif.hayazee.data.home.HomeServiceRepositoryImpl
import com.nadhif.hayazee.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object HomeModule {

    @Provides
    @FragmentScoped
    fun provideHomeServiceRepository(
        apiService: ApiService
    ): HomeRepository {
        return HomeServiceRepositoryImpl(apiService)
    }
}