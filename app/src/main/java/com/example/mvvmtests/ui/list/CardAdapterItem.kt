package com.example.mvvmtests.ui.list

import androidx.annotation.DrawableRes
import com.example.mvvmtests.adapter.AdapterItem

data class CardAdapterItem(val id: String, val number: String, @DrawableRes val logo: Int) :
    AdapterItem {
    override val uniqueTag: String = "CardAdapterItem{id}"
}