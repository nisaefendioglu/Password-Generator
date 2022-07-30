package com.nisaefendioglu.passwordgenerator.data.di

import com.nisaefendioglu.passwordgenerator.data.remote.api.ApiService
import com.nisaefendioglu.passwordgenerator.data.remote.source.PasswordRemoteDataSource
import com.nisaefendioglu.passwordgenerator.data.remote.source.impl.PasswordRemoteDataSourceImpl
import com.nisaefendioglu.passwordgenerator.data.repository.PasswordRepositoryImpl
import com.nisaefendioglu.passwordgenerator.domain.repository.PasswordRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class PasswordModule {

    @Provides
    fun providePasswordRemoteDataSource(apiService: ApiService): PasswordRemoteDataSource =
        PasswordRemoteDataSourceImpl(apiService)

    @Provides
    fun providePasswordRepository(
        passwordRemoteDataSource: PasswordRemoteDataSource,
    ): PasswordRepository =
        PasswordRepositoryImpl(passwordRemoteDataSource)
}