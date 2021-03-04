package com.example.mvvmtests.data

import android.content.Context
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor

@Module
class MockedInterceptorModule {

    @Provides
    @MockedRequests
    fun providesMockedInterceptor(context: Context): Interceptor? = null
}