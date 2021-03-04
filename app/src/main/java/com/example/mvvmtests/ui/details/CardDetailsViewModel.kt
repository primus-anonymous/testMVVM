package com.example.mvvmtests.ui.details

import androidx.annotation.DrawableRes
import com.example.mvvmtests.BaseViewModel
import com.example.mvvmtests.R
import com.example.mvvmtests.cases.CaseResult
import com.example.mvvmtests.cases.LoadCardDetailsUseCase
import com.example.mvvmtests.domain.CardType
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class CardDetailsViewModel(
    private val loadCardDetailsUseCase: LoadCardDetailsUseCase
) : BaseViewModel() {

    private val cardDetailsSubject = BehaviorSubject.create<DisplayCardData>()
    private val progressSubject = BehaviorSubject.createDefault(false)
    private val retrySubject = BehaviorSubject.createDefault(false)

    val cardDetailsObservable: Observable<DisplayCardData> = cardDetailsSubject
    val progressObservable: Observable<Boolean> = progressSubject
    val retryObservable: Observable<Boolean> = retrySubject

    fun loadCardDetails(cardId: String) {

        progressSubject.onNext(true)
        retrySubject.onNext(false)

        inViewModelScope {
            loadCardDetailsUseCase(cardId)
                .subscribeOn(Schedulers.io())
                .subscribe { result ->

                    progressSubject.onNext(false)

                    when (result) {
                        is CaseResult.Success -> {
                            val icon = when (result.value.cardType) {
                                CardType.MIR -> R.drawable.ic_card3
                                CardType.VISA -> R.drawable.ic_card2
                                CardType.MASTERCARD -> R.drawable.ic_card1
                            }

                            cardDetailsSubject.onNext(
                                DisplayCardData(
                                    result.value.name,
                                    result.value.number,
                                    icon
                                )
                            )
                        }

                        is CaseResult.Failure -> {
                            retrySubject.onNext(true)
                        }
                    }
                }
        }
    }

    data class DisplayCardData(val name: String, val number: String, @DrawableRes val cardLogo: Int)
}