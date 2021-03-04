package com.example.mvvmtests.ui

import com.example.mvvmtests.ui.details.CardDetailsFragment
import com.example.mvvmtests.ui.list.CardListFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun CardsList() = FragmentScreen { CardListFragment() }
    fun CardsDetails(cardId: String) = FragmentScreen { CardDetailsFragment.instance(cardId) }
}
