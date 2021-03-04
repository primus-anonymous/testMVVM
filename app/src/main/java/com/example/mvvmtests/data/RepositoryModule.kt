package com.example.mvvmtests.data

import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindsRepo(impl: CardRepositoryImpl): CardRepository
}