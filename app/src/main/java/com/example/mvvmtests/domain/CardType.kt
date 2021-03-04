package com.example.mvvmtests.domain

import com.squareup.moshi.Json

enum class CardType {
    @Json(name = "visa")
    VISA,

    @Json(name = "mastercard")
    MASTERCARD,

    @Json(name = "mir")
    MIR
}