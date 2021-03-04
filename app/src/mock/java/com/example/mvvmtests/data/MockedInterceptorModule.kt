package com.example.mvvmtests.data

import android.content.Context
import com.example.mvvmtests.BuildConfig
import com.example.mvvmtests.R
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.mock.*
import javax.inject.Singleton

@Module
class MockedInterceptorModule {

    @Provides
    @Singleton
    @MockedRequests
    fun providesMockedInterceptor(context: Context): Interceptor = MockInterceptor(Behavior.UNORDERED).apply {

        rule(get, url eq "${BuildConfig.BASE_URL}${CardApi.CARD_LIST}", times = anyTimes) {
            respond(
                    AndroidResources.rawRes(context, R.raw.get_cards_list),
                    MediaTypes.MEDIATYPE_JSON
            )
        }
        rule(get, url eq "${BuildConfig.BASE_URL}/cards/603dffb2125fa2b60ba9a0ef", times = anyTimes) {
            respond(
                    AndroidResources.rawRes(context, R.raw.get_card_details),
                    MediaTypes.MEDIATYPE_JSON
            )
        }
    }
}