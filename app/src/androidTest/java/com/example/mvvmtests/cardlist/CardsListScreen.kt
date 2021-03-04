package com.example.mvvmtests.cardlist

import android.view.View
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.progress.KProgressBar
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView
import com.example.mvvmtests.R
import org.hamcrest.Matcher

object CardsListScreen : Screen<CardsListScreen>() {
    val emptyMessage = KTextView { withId(R.id.empty_message) }
    val retryButton = KButton { withId(R.id.btn_retry) }
    val progress = KProgressBar { withId(R.id.progress) }

    val cardsList: KRecyclerView = KRecyclerView({
        withId(R.id.cards_list)
    }, itemTypeBuilder = {
        itemType(::CardItem)
    })

    class CardItem(parent: Matcher<View>) : KRecyclerItem<CardItem>(parent) {
        val cardNumber: KTextView = KTextView(parent) { withId(R.id.card_number) }
        val cardLogo: KImageView = KImageView(parent) { withId(R.id.card_logo) }
    }
}


