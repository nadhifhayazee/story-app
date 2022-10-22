package com.nadhif.hayazee.data.di

import com.nadhif.hayazee.data.auth.AuthRepository
import com.nadhif.hayazee.data.auth.AuthServiceRepositoryImpl
import com.nadhif.hayazee.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object AuthModule {

    @Provides
    @FragmentScoped
    fun provideAuthServiceRepository(
        apiService: ApiService
    ): AuthRepository {
        return AuthServiceRepositoryImpl(apiService)
    }
}