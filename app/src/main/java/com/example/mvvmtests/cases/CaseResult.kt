package com.example.mvvmtests.cases

import com.example.mvvmtests.error.AppError

sealed class CaseResult<ResultType> {
    data class Success<ResultType>(val value: ResultType): CaseResult<ResultType>()
    data class Failure<ResultType>(val error: AppError): CaseResult<ResultType>()
}