package com.example.mvvmtests

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel() {

    private val viewModelDisposable = CompositeDisposable()

    fun inViewModelScope(subscriber: () -> Disposable) {
        viewModelDisposable.add(subscriber.invoke())
    }

    override fun onCleared() {
        viewModelDisposable.clear()
        super.onCleared()
    }
}