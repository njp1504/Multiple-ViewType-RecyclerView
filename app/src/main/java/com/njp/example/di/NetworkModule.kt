package com.njp.example.di

import com.njp.example.data.remote.github.GithubService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideGithubService() = GithubService.api
}