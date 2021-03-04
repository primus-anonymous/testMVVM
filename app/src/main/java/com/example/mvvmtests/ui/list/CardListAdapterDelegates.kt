package com.example.mvvmtests.ui.list

import android.widget.ImageView
import android.widget.TextView
import com.example.mvvmtests.R
import com.example.mvvmtests.adapter.AdapterItem
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate

object CardListAdapterDelegates {

    fun cardListItem(itemClick: (CardAdapterItem) -> Unit) = adapterDelegate<CardAdapterItem, AdapterItem>(R.layout.item_card_list) {

        val cardNumber: TextView = findViewById(R.id.card_number)
        val cardLogo: ImageView = findViewById(R.id.card_logo)

        itemView.setOnClickListener { itemClick(item) }

        bind {
            cardNumber.text = item.number
            cardLogo.setImageResource(item.logo)
        }
    }

}