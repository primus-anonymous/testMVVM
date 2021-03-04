package com.example.mvvmtests.di

import com.example.mvvmtests.cardlist.CardListTest
import com.example.mvvmtests.data.DataModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, NavigationModule::class, DataModule::class])
interface TestApplicaitonComponent : ApplicationComponent {

    fun inject(cardListTest: CardListTest)
}