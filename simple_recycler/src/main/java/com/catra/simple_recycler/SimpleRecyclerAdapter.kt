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

    fun addItemCount(addElements: Int) {
        itemCount = addElements
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
        return Holder(view)
    }

    override fun getItemCount() = itemCount

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        view.invoke(holder.itemView, position)
    }

}

class Holder(view: View) : RecyclerView.ViewHolder(view)
