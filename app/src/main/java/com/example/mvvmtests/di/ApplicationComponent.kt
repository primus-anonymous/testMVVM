package com.example.mvvmtests.di

import com.example.mvvmtests.MainActivity
import com.example.mvvmtests.data.DataModule
import com.example.mvvmtests.ui.details.CardDetailsFragment
import com.example.mvvmtests.ui.list.CardListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, NavigationModule::class, DataModule::class])
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(cardListFragment: CardListFragment)

    fun inject(cardDetailsFragment: CardDetailsFragment)
}