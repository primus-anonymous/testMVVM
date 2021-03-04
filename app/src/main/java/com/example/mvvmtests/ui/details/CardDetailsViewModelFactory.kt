package com.example.mvvmtests.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmtests.cases.LoadCardDetailsUseCase
import javax.inject.Inject


class CardDetailsViewModelFactory @Inject constructor(private val loadCardDetailsUseCase: LoadCardDetailsUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CardDetailsViewModel(loadCardDetailsUseCase) as T
}
