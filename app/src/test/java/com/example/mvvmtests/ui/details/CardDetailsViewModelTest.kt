package com.example.mvvmtests.ui.details

import com.example.mvvmtests.R
import com.example.mvvmtests.RxRule
import com.example.mvvmtests.adapter.AdapterItem
import com.example.mvvmtests.cases.CaseResult
import com.example.mvvmtests.cases.LoadCardDetailsUseCase
import com.example.mvvmtests.domain.Card
import com.example.mvvmtests.domain.CardDetails
import com.example.mvvmtests.domain.CardType
import com.example.mvvmtests.error.AppError
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class CardDetailsViewModelTest {

    @Rule
    @JvmField
    val rxRule = RxRule()

    lateinit var testable: CardDetailsViewModel

    @Mock
    lateinit var getCardDetailsCase: LoadCardDetailsUseCase

    @Before
    fun setUp() {
        testable = CardDetailsViewModel(getCardDetailsCase)
    }

    @Test
    fun `progress on happy path`() {
        val cardId = "cardId"

        whenever(getCardDetailsCase.invoke(cardId)).thenAnswer {
            Single.just(
                CaseResult.Success(
                    listOf<Card>()
                )
            )
        }

        val progress = testable.progressObservable.test()

        testable.loadCardDetails(cardId)

        progress.assertValues(false, true, false)
    }

    @Test
    fun `progress on error path`() {
        val cardId = "cardId"

        whenever(getCardDetailsCase.invoke(cardId)).thenAnswer {
            Single.just(
                CaseResult.Failure<AdapterItem>(
                    AppError.NoNetwork
                )
            )
        }

        val progress = testable.progressObservable.test()

        testable.loadCardDetails(cardId)

        progress.assertValues(false, true, false)
    }

    @Test
    fun `retry on happy path`() {
        val cardId = "cardId"

        whenever(getCardDetailsCase.invoke(cardId)).thenAnswer {
            Single.just(
                CaseResult.Success(
                    listOf<Card>()
                )
            )
        }

        val retryVisibility = testable.retryObservable.test()

        testable.loadCardDetails(cardId)

        retryVisibility.assertValues(false, false)
    }

    @Test
    fun `retry on error path`() {
        val cardId = "cardId"

        whenever(getCardDetailsCase.invoke(cardId)).thenAnswer {
            Single.just(
                CaseResult.Failure<Card>(
                    AppError.Unknown
                )
            )
        }

        val retryVisibility = testable.retryObservable.test()

        testable.loadCardDetails(cardId)

        retryVisibility.assertValues(false, false, true)
    }

    @Test
    fun `card details on error path`() {
        val cardId = "cardId"

        whenever(getCardDetailsCase.invoke(cardId)).thenAnswer {
            Single.just(
                CaseResult.Failure<Card>(
                    AppError.Unknown
                )
            )
        }

        val cardsList = testable.cardDetailsObservable.test()

        testable.loadCardDetails(cardId)

        cardsList.assertNoValues()
    }

    @Test
    fun `card details on happy path with master card`() {
        val cardId = "cardId"

        val source = CardDetails("id1", "name", "number1", CardType.MASTERCARD)

        whenever(getCardDetailsCase.invoke(cardId)).thenAnswer {
            Single.just(
                CaseResult.Success(
                    source
                )
            )
        }

        val cardsList = testable.cardDetailsObservable.test()

        testable.loadCardDetails(cardId)

        val expected = CardDetailsViewModel.DisplayCardData("name", "number1", R.drawable.ic_card1)

        cardsList.assertValues(expected)
    }

    @Test
    fun `card details on happy path with visa card`() {
        val cardId = "cardId"

        val source = CardDetails("id1", "name", "number1", CardType.VISA)

        whenever(getCardDetailsCase.invoke(cardId)).thenAnswer {
            Single.just(
                CaseResult.Success(
                    source
                )
            )
        }

        val cardsList = testable.cardDetailsObservable.test()

        testable.loadCardDetails(cardId)

        val expected = CardDetailsViewModel.DisplayCardData("name", "number1", R.drawable.ic_card2)

        cardsList.assertValues(expected)
    }

    @Test
    fun `card details on happy path with mir card`() {
        val cardId = "cardId"

        val source = CardDetails("id1", "name", "number1", CardType.MIR)

        whenever(getCardDetailsCase.invoke(cardId)).thenAnswer {
            Single.just(
                CaseResult.Success(
                    source
                )
            )
        }

        val cardsList = testable.cardDetailsObservable.test()

        testable.loadCardDetails(cardId)

        val expected = CardDetailsViewModel.DisplayCardData("name", "number1", R.drawable.ic_card3)

        cardsList.assertValues(expected)
    }
}