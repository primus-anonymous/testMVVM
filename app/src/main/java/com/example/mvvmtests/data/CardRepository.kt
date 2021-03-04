package com.example.mvvmtests.data

import com.example.mvvmtests.domain.Card
import com.example.mvvmtests.domain.CardDetails
import io.reactivex.Single

interface CardRepository {

    fun cards(): Single<List<Card>>

    fun cardDetails(id: String): Single<CardDetails>
}