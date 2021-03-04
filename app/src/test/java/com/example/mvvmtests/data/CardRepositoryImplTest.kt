package com.example.mvvmtests.data

import com.example.mvvmtests.RxRule
import com.example.mvvmtests.domain.Card
import com.example.mvvmtests.domain.CardDetails
import com.example.mvvmtests.domain.CardType
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CardRepositoryImplTest {

    @Rule
    @JvmField
    val rxRule = RxRule()

    @Mock
    lateinit var cardApi: CardApi

    lateinit var testable: CardRepositoryImpl

    @Before
    fun setUp() {
        testable = CardRepositoryImpl(cardApi)
    }

    @Test
    fun `requesting cards list via api`() {
        val someResult = listOf(
            Card("id1", "number1", CardType.MASTERCARD),
            Card("id2", "number2", CardType.VISA),
            Card("id3", "number3", CardType.MIR)
        )
        whenever(cardApi.cardsList()).thenAnswer { Single.just(someResult) }

        testable.cards().test().assertValue(someResult)
    }

    @Test
    fun `requesting card details`() {
        val requestedId = "requestedId"
        val someResult =
            CardDetails("id1", "name1", "number1", CardType.MASTERCARD)
        whenever(cardApi.cardsDetails(requestedId)).thenAnswer { Single.just(someResult) }

        testable.cardDetails(requestedId).test().assertValue(someResult)
    }
}