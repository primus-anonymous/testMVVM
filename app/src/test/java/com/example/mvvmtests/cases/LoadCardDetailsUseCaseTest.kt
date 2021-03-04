package com.example.mvvmtests.cases

import com.example.mvvmtests.RxRule
import com.example.mvvmtests.data.CardRepository
import com.example.mvvmtests.domain.Card
import com.example.mvvmtests.domain.CardDetails
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
class LoadCardDetailsUseCaseTest {

    @Rule
    @JvmField
    val rxRule = RxRule()

    @Mock
    lateinit var errorHandler: DataErrorHandler

    @Mock
    lateinit var cardRepository: CardRepository

    lateinit var testable: LoadCardDetailsUseCase

    @Before
    fun setUp() {
        testable = LoadCardDetailsUseCase(cardRepository, errorHandler)
    }

    @Test
    fun `case completed successfully`() {

        val cardId = "cardId"

        val repoResult = CardDetails("id1", "name", "number", CardType.VISA)

        whenever(cardRepository.cardDetails(cardId)).thenAnswer { Single.just(repoResult) }

        testable.invoke(cardId).test()
            .assertValue(CaseResult.Success(repoResult))
    }

    @Test
    fun `case completed with failure`() {
        val cardId = "cardId"

        whenever(errorHandler.resolveError(any())).thenAnswer { AppError.Unknown }
        whenever(cardRepository.cardDetails(cardId)).thenAnswer {
            Single.error<List<Card>>(
                RuntimeException()
            )
        }

        testable.invoke(cardId).test()
            .assertValue(CaseResult.Failure(AppError.Unknown))
    }

}