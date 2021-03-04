package com.example.mvvmtests.data

import com.example.mvvmtests.domain.Card
import com.example.mvvmtests.domain.CardDetails
import io.reactivex.Single
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(private val cardApi: CardApi) : CardRepository {

    override fun cards(): Single<List<Card>> = cardApi.cardsList()

    override fun cardDetails(id: String): Single<CardDetails> = cardApi.cardsDetails(id)
}