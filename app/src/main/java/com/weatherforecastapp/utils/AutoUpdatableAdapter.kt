package com.weatherforecastapp.utils

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

interface AutoUpdatableAdapter {

    fun <T> RecyclerView.Adapter<*>.autoNotify(
        old: List<T>,
        new: List<T>,
        payload: Any? = null,
        compare: (T, T) -> Boolean
    ) {
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return compare(old[oldItemPosition], new[newItemPosition])
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return old[oldItemPosition] == new[newItemPosition]
            }

            override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
                return if (areContentsTheSame(oldItemPosition, newItemPosition)) null else payload
            }

            override fun getOldListSize() = old.size

            override fun getNewListSize() = new.size
        }).dispatchUpdatesTo(this)
    }
}