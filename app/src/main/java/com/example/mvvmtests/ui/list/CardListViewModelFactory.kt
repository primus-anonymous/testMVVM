package com.example.mvvmtests.ui.list

import androidx.lifecycle.ViewModel

import androidx.lifecycle.ViewModelProvider
import com.example.mvvmtests.cases.LoadAllCardsUseCase
import javax.inject.Inject

class CardListViewModelFactory @Inject constructor(private val loadAllCardsUseCase: LoadAllCardsUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CardListViewModel(loadAllCardsUseCase) as T
}
