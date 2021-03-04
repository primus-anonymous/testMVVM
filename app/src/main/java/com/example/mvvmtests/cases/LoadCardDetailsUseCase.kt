package com.example.mvvmtests.cases

import com.example.mvvmtests.data.CardRepository
import com.example.mvvmtests.domain.CardDetails
import com.example.mvvmtests.error.DataErrorHandler
import io.reactivex.Single
import javax.inject.Inject


class LoadCardDetailsUseCase @Inject constructor(
    private val repository: CardRepository,
    errorHandler: DataErrorHandler
) : ErrorHandingUseCase<String, CardDetails>(errorHandler) {

    override fun run(param: String): Single<CardDetails> = repository.cardDetails(param)
}