package com.njp.example.di

import android.app.Application
import com.njp.example.MyApp
import com.njp.example.data.GithubRepository
import com.njp.example.data.remote.github.GithubService
import dagger.BindsInstance
import dagger.Component
import dagger.Provides
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ViewModelModule::class,
    RepositoryModule::class,
    NetworkModule::class
])
interface AppComponent : AndroidInjector<MyApp> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application) : AppComponent
    }
}