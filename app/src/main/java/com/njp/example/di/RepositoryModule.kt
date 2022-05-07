package com.njp.example.di

import com.njp.example.data.GithubRepository
import com.njp.example.data.remote.github.GithubService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {


    @Provides
    @Singleton
    fun provideGithubRepository(api: GithubService) = GithubRepository(api)
}