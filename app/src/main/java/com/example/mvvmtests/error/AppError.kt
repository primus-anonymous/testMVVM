package com.example.mvvmtests.error

sealed class AppError {
    object NoNetwork : AppError()
    object Unknown : AppError()
}