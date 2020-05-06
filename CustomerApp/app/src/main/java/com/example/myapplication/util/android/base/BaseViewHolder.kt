package com.example.myapplication.util.android.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * Base class for RecyclerView ViewHolders
 * @param <VB> ViewBinding class generated for the corresponding layout
 * @param <T> type of item being held
</T></VB> */
abstract class BaseViewHolder<VB : ViewBinding, T>(protected val binding: VB): RecyclerView.ViewHolder(binding.root) {
    abstract fun bindTo(item: T?)
}