package com.example.mvvmtests.cases

import com.example.mvvmtests.error.DataErrorHandler
import io.reactivex.Single

interface UseCase<ParamType, ResultType> {
    operator fun invoke(param: ParamType): Single<CaseResult<ResultType>>
}

abstract class ErrorHandingUseCase<ParamType, ResultType>(
    errorHandler: DataErrorHandler,
) :
    UseCase<ParamType, ResultType> {

    private val errorResponseTransformer: ErrorResponseTransformer<ResultType> =
        ErrorResponseTransformer(
            errorHandler
        )

    abstract fun run(param: ParamType): Single<ResultType>

    override fun invoke(param: ParamType): Single<CaseResult<ResultType>> =
        run(param).compose(errorResponseTransformer)
}