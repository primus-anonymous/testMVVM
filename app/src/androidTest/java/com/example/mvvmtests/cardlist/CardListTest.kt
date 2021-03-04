package com.example.mvvmtests.cardlist

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.example.mvvmtests.BuildConfig
import com.example.mvvmtests.TestApp
import com.example.mvvmtests.data.CardApi
import com.example.mvvmtests.data.MockedRequests
import com.example.mvvmtests.test.R
import com.example.mvvmtests.ui.list.CardListFragment
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import okhttp3.Interceptor
import okhttp3.mock.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class CardListTest : TestCase() {

    @Inject
    @MockedRequests
    lateinit var mockedInterceptor: Interceptor

    @Before
    fun setUp() {
        val app = getInstrumentation().targetContext.applicationContext as TestApp

        app.testComponent.inject(this@CardListTest)
    }

    @Test
    fun shouldDisplayEmptyMessage() {
        before {

            with(mockedInterceptor as MockInterceptor) {
                reset()

                rule(get, url eq "${BuildConfig.BASE_URL}${CardApi.CARD_LIST}", times = anyTimes) {
                    respond(
                            getInstrumentation().context.resources.openRawResource(R.raw.get_cards_list_empty),
                            MediaTypes.MEDIATYPE_JSON
                    )
                }
            }

            launchFragmentInContainer<CardListFragment>(themeResId = com.example.mvvmtests.R.style.Theme_MVVMTests)
        }.after {

        }.run {
            step("Check empty message visibility") {
                CardsListScreen {
                    emptyMessage {
                        isVisible()
                        hasText(com.example.mvvmtests.R.string.empty_message)
                    }
                    retryButton {
                        isGone()
                    }
                }
            }
        }
    }

    @Test
    fun shouldDisplayRetryButton() {
        before {

            with(mockedInterceptor as MockInterceptor) {
                reset()

                rule(get, url eq "${BuildConfig.BASE_URL}${CardApi.CARD_LIST}", times = anyTimes) {
                    respond(404)
                }
            }

            launchFragmentInContainer<CardListFragment>(themeResId = com.example.mvvmtests.R.style.Theme_MVVMTests)

        }.after {

        }.run {
            step("Check retry button visibility") {
                CardsListScreen {
                    retryButton {
                        isVisible()
                        hasText(com.example.mvvmtests.R.string.btn_retry)
                    }
                    emptyMessage {
                        isGone()
                    }
                }
            }
        }
    }

    @Test
    fun shouldDisplayCardsList() {
        before {

            with(mockedInterceptor as MockInterceptor) {
                reset()

                rule(get, url eq "${BuildConfig.BASE_URL}${CardApi.CARD_LIST}", times = anyTimes) {
                    respond(
                            getInstrumentation().context.resources.openRawResource(R.raw.get_cards_list),
                            MediaTypes.MEDIATYPE_JSON
                    )
                }
            }

            launchFragmentInContainer<CardListFragment>(themeResId = com.example.mvvmtests.R.style.Theme_MVVMTests)
        }.after {

        }.run {
            step("Check cards list") {
                CardsListScreen {
                    emptyMessage {
                        isGone()
                    }
                    retryButton {
                        isGone()
                    }

                    scenario(CheckListScenario)
                }
            }
        }
    }

    @Test
    fun shouldRetry() {
        before {

            with(mockedInterceptor as MockInterceptor) {
                reset()

                rule(get, url eq "${BuildConfig.BASE_URL}${CardApi.CARD_LIST}", times = 1) {
                    respond(404)
                }

                rule(get, url eq "${BuildConfig.BASE_URL}${CardApi.CARD_LIST}", times = 1) {
                    respond(
                            getInstrumentation().context.resources.openRawResource(R.raw.get_cards_list),
                            MediaTypes.MEDIATYPE_JSON
                    )
                }
            }
            launchFragmentInContainer<CardListFragment>(themeResId = com.example.mvvmtests.R.style.Theme_MVVMTests)
        }.after {

        }.run {
            step("Check cards list") {
                CardsListScreen {
                    emptyMessage {
                        isGone()
                    }
                    retryButton {
                        click()
                    }

                    scenario(CheckListScenario)
                }
            }
        }
    }
}