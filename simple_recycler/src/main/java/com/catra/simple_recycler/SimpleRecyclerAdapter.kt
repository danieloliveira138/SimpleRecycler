package com.catra.simple_recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class SimpleRecyclerAdapter (
    private val layoutRes: Int,
    private val view: (View, Int) -> Unit,
    private var itemCount: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var loading = false
        set(value) {
            if (field == value) return
            field = value
            if (value) itemCount + 1 else itemCount - 1
            notifyDataSetChanged()
        }

    fun addItemCount(addElements: Int) {
        if (addElements == itemCount) return
        itemCount = addElements
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int) =
        if (isLoadingPosition(position)) R.layout.loading_holder else layoutRes

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return Holder(view)
    }

    override fun getItemCount() = itemCount

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (isLoadingPosition(position)) return
        view.invoke(holder.itemView, position)
    }

    private fun isLoadingPosition(position: Int) = loading && itemCount == position + 1
}

class Holder(view: View) : RecyclerView.ViewHolder(view)
