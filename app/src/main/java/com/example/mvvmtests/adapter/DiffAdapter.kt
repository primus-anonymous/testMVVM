package com.example.mvvmtests.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class DiffAdapter(vararg delegates: AdapterDelegate<List<AdapterItem>>) :
    AsyncListDifferDelegationAdapter<AdapterItem>(
        object : DiffUtil.ItemCallback<AdapterItem>() {
            override fun areItemsTheSame(oldItem: AdapterItem, newItem: AdapterItem): Boolean =
                oldItem.uniqueTag == newItem.uniqueTag

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: AdapterItem, newItem: AdapterItem): Boolean =
                oldItem == newItem
        },
        AdapterDelegatesManager(*delegates)
    )
