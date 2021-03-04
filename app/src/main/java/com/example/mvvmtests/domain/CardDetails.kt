package com.example.mvvmtests.domain

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CardDetails(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "number") val number: String,
    @Json(name = "type") val cardType: CardType
)