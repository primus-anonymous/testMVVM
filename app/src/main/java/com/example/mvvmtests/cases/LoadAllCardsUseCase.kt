package com.example.mvvmtests.cases

import com.example.mvvmtests.data.CardRepository
import com.example.mvvmtests.domain.Card
import com.example.mvvmtests.error.DataErrorHandler
import io.reactivex.Single
import javax.inject.Inject


class LoadAllCardsUseCase @Inject constructor(
    private val repository: CardRepository,
    errorHandler: DataErrorHandler
) : ErrorHandingUseCase<Unit, List<Card>>(errorHandler) {

    override fun run(param: Unit): Single<List<Card>> = repository.cards()

}