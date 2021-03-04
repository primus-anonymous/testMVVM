package com.example.mvvmtests.cases

import com.example.mvvmtests.error.DataErrorHandler
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.SingleTransformer

class ErrorResponseTransformer<InputType>(private val errorHandler: DataErrorHandler) :
    SingleTransformer<InputType, CaseResult<InputType>> {

    override fun apply(upstream: Single<InputType>): SingleSource<CaseResult<InputType>> {

        val happyPath: Single<CaseResult<InputType>> = upstream
            .map { result: InputType -> CaseResult.Success(result) }

        return happyPath
            .onErrorResumeNext { throwable ->
                val error =
                    CaseResult.Failure<InputType>(errorHandler.resolveError(throwable))
                Single.just(error)
            }
    }
}