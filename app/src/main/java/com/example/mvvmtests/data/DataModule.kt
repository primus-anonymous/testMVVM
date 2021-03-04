package com.example.mvvmtests.data

import dagger.Module

@Module(includes = [ApiModule::class, RepositoryModule::class, MockedInterceptorModule::class])
object DataModule
