package com.example.mvvmtests.ui

import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseFragment : Fragment() {

    private val startStopDisposable = CompositeDisposable()

    override fun onStop() {
        startStopDisposable.clear()
        super.onStop()
    }

    fun inStartStopScope(subscriber: () -> Disposable) {
        startStopDisposable.add(subscriber.invoke())
    }
}