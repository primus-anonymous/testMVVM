package com.example.mvvmtests.data

import com.example.mvvmtests.BuildConfig
import com.example.mvvmtests.error.DataErrorHandler
import com.example.mvvmtests.error.DataErrorHandlerImpl
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory


@Module
class ApiModule {

    @Provides
    fun providesApi(okHttpClient: OkHttpClient): CardApi {

        val moshi = Moshi.Builder().build()

        val builder = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(okHttpClient)
                .build()

        return builder.create(CardApi::class.java)
    }


    @Provides
    fun providesOkHttpClient(@MockedRequests mockInterceptor: Interceptor?): OkHttpClient {
        val builder = OkHttpClient.Builder()

        mockInterceptor?.apply(builder::addInterceptor)

        return builder.build()
    }

    @Provides
    fun providesErrorHandler(): DataErrorHandler = DataErrorHandlerImpl()
}