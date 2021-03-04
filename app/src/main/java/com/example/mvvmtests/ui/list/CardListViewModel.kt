package com.example.mvvmtests.ui.list

import com.example.mvvmtests.BaseViewModel
import com.example.mvvmtests.R
import com.example.mvvmtests.adapter.AdapterItem
import com.example.mvvmtests.cases.CaseResult
import com.example.mvvmtests.cases.LoadAllCardsUseCase
import com.example.mvvmtests.domain.CardType
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class CardListViewModel(
        private val loadAllCardsUseCase: LoadAllCardsUseCase,
        loadOnInit: Boolean = true
) : BaseViewModel() {

    private val cardsSubject = BehaviorSubject.createDefault(listOf<AdapterItem>())
    private val progressSubject = BehaviorSubject.createDefault(false)
    private val retrySubject = BehaviorSubject.createDefault(false)
    private val emptySubject = BehaviorSubject.createDefault(false)

    val cardsObservable: Observable<List<AdapterItem>> = cardsSubject
    val progressObservable: Observable<Boolean> = progressSubject
    val retryObservable: Observable<Boolean> = retrySubject
    val emptyObservable: Observable<Boolean> = emptySubject

    init {
        if (loadOnInit) {
            loadCards()
        }
    }

    fun loadCards() {

        progressSubject.onNext(true)
        retrySubject.onNext(false)
        emptySubject.onNext(false)

        inViewModelScope {
            loadAllCardsUseCase(Unit)
                    .subscribeOn(Schedulers.io())
                    .subscribe { result ->

                        progressSubject.onNext(false)

                        when (result) {
                            is CaseResult.Success -> {
                                val adapterItems = result.value.map { card ->
                                    val icon = when (card.type) {
                                        CardType.MIR -> R.drawable.ic_card3
                                        CardType.VISA -> R.drawable.ic_card2
                                        CardType.MASTERCARD -> R.drawable.ic_card1
                                    }
                                    CardAdapterItem(
                                            card.id,
                                            card.number,
                                            icon
                                    )
                                }
                                cardsSubject.onNext(adapterItems)

                                emptySubject.onNext(adapterItems.isEmpty())
                            }

                            is CaseResult.Failure -> {
                                retrySubject.onNext(true)
                                cardsSubject.onNext(listOf())
                            }
                        }
                    }
        }
    }
}