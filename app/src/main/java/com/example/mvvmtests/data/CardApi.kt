package com.example.mvvmtests.data

import com.example.mvvmtests.domain.Card
import com.example.mvvmtests.domain.CardDetails
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface CardApi {

    companion object {
        const val CARD_LIST = "/cards/list"
        const val CARD_DETAILS = "/cards/{id}"
    }

    @GET(CARD_LIST)
    fun cardsList(): Single<List<Card>>

    @GET(CARD_DETAILS)
    fun cardsDetails(@Path("id") id: String): Single<CardDetails>
}