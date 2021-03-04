package com.example.mvvmtests.error

import java.io.IOException

interface DataErrorHandler {

    fun resolveError(throwable: Throwable): AppError
}

class DataErrorHandlerImpl : DataErrorHandler {

    override fun resolveError(throwable: Throwable): AppError =
        if (throwable is IOException) AppError.NoNetwork else AppError.Unknown

}