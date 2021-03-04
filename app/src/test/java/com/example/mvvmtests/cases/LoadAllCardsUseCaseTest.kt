package com.example.mvvmtests.cases

import com.example.mvvmtests.RxRule
import com.example.mvvmtests.data.CardRepository
import com.example.mvvmtests.domain.Card
import com.example.mvvmtests.domain.CardType
import com.example.mvvmtests.error.AppError
import com.example.mvvmtests.error.DataErrorHandler
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoadAllCardsUseCaseTest {

    @Rule
    @JvmField
    val rxRule = RxRule()

    @Mock
    lateinit var errorHandler: DataErrorHandler

    @Mock
    lateinit var cardRepository: CardRepository

    lateinit var testable: LoadAllCardsUseCase

    @Before
    fun setUp() {
        testable = LoadAllCardsUseCase(cardRepository, errorHandler)
    }

    @Test
    fun `case completed successfully`() {

        val repoResult = listOf(
            Card("id1", "number1", CardType.MASTERCARD),
            Card("id2", "number2", CardType.VISA),
            Card("id3", "number3", CardType.MIR)
        )

        whenever(cardRepository.cards()).thenAnswer { Single.just(repoResult) }

        testable.invoke(Unit).test()
            .assertValue(CaseResult.Success(repoResult))
    }

    @Test
    fun `case completed with failure`() {

        whenever(errorHandler.resolveError(any())).thenAnswer { AppError.Unknown }
        whenever(cardRepository.cards()).thenAnswer { Single.error<List<Card>>(RuntimeException()) }

        testable.invoke(Unit).test()
            .assertValue(CaseResult.Failure(AppError.Unknown))
    }

}