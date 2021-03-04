package com.example.mvvmtests.ui.list

import com.example.mvvmtests.R
import com.example.mvvmtests.RxRule
import com.example.mvvmtests.adapter.AdapterItem
import com.example.mvvmtests.error.AppError
import com.example.mvvmtests.cases.CaseResult
import com.example.mvvmtests.cases.LoadAllCardsUseCase
import com.example.mvvmtests.domain.Card
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
class CardListViewModelTest {

    @Rule
    @JvmField
    val rxRule = RxRule()

    lateinit var testable: CardListViewModel

    @Mock
    lateinit var getCardsCase: LoadAllCardsUseCase

    @Before
    fun setUp() {
        testable = CardListViewModel(getCardsCase, false)
    }

    @Test
    fun `progress on happy path`() {

        whenever(getCardsCase.invoke(Unit)).thenAnswer { Single.just(CaseResult.Success(listOf<Card>())) }

        val progress = testable.progressObservable.test()

        testable.loadCards()

        progress.assertValues(false, true, false)
    }

    @Test
    fun `progress on error path`() {

        whenever(getCardsCase.invoke(Unit)).thenAnswer {
            Single.just(
                    CaseResult.Failure<AdapterItem>(
                            AppError.NoNetwork
                    )
            )
        }

        val progress = testable.progressObservable.test()

        testable.loadCards()

        progress.assertValues(false, true, false)
    }

    @Test
    fun `retry on happy path`() {

        whenever(getCardsCase.invoke(Unit)).thenAnswer { Single.just(CaseResult.Success(listOf<Card>())) }

        val retryVisibility = testable.retryObservable.test()

        testable.loadCards()

        retryVisibility.assertValues(false, false)
    }

    @Test
    fun `retry on error path`() {

        whenever(getCardsCase.invoke(Unit)).thenAnswer {
            Single.just(
                    CaseResult.Failure<Card>(
                            AppError.Unknown
                    )
            )
        }

        val retryVisibility = testable.retryObservable.test()

        testable.loadCards()

        retryVisibility.assertValues(false, false, true)
    }

    @Test
    fun `empty on happy path`() {

        whenever(getCardsCase.invoke(Unit)).thenAnswer { Single.just(CaseResult.Success(listOf<Card>())) }

        val empty = testable.emptyObservable.test()

        testable.loadCards()

        empty.assertValues(false, false, true)
    }

    @Test
    fun `empty on error path`() {

        whenever(getCardsCase.invoke(Unit)).thenAnswer {
            Single.just(
                    CaseResult.Failure<Card>(
                            AppError.Unknown
                    )
            )
        }

        val empty = testable.emptyObservable.test()

        testable.loadCards()

        empty.assertValues(false, false)
    }

    @Test
    fun `cards list on error path`() {

        whenever(getCardsCase.invoke(Unit)).thenAnswer {
            Single.just(
                    CaseResult.Failure<Card>(
                            AppError.Unknown
                    )
            )
        }

        val cardsList = testable.cardsObservable.test()

        testable.loadCards()

        cardsList.assertValues(listOf(), listOf())
    }

    @Test
    fun `cards list on happy path`() {

        val source = listOf(
                Card("id1", "number1", CardType.MASTERCARD),
                Card("id2", "number2", CardType.VISA),
                Card("id3", "number3", CardType.MIR),
        )

        whenever(getCardsCase.invoke(Unit)).thenAnswer { Single.just(CaseResult.Success(source)) }

        val cardsList = testable.cardsObservable.test()

        testable.loadCards()

        val expected = listOf(
                CardAdapterItem("id1", "number1", R.drawable.ic_card1),
                CardAdapterItem("id2", "number2", R.drawable.ic_card2),
                CardAdapterItem("id3", "number3", R.drawable.ic_card3)
        )

        cardsList.assertValues(listOf(), expected)
    }

    @Test
    fun `cards list fetch on init`() {

        val source = listOf(
                Card("id1", "number1", CardType.MASTERCARD),
                Card("id2", "number2", CardType.VISA),
                Card("id3", "number3", CardType.MIR),
        )

        whenever(getCardsCase.invoke(Unit)).thenAnswer { Single.just(CaseResult.Success(source)) }

        testable = CardListViewModel(getCardsCase, true)

        val cardsList = testable.cardsObservable.test()

        val expected = listOf(
                CardAdapterItem("id1", "number1", R.drawable.ic_card1),
                CardAdapterItem("id2", "number2", R.drawable.ic_card2),
                CardAdapterItem("id3", "number3", R.drawable.ic_card3)
        )

        cardsList.assertValues(expected)
    }
}