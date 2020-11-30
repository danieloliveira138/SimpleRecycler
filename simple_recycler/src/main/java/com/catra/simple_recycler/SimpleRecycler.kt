package com.catra.simple_recycler

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class SimpleRecycler(
    context: Context,
    attrs: AttributeSet?
) : ConstraintLayout(context, attrs) {

    private val recycler: RecyclerView
    private val attributes: TypedArray

    fun listBuilder(
        layoutRes: Int,
        itemCount: Int,
        builder: (View, Int) -> Unit
    ) {

        recycler.apply {
            adapter = SimpleRecyclerAdapter(
                layoutRes = layoutRes,
                itemCount = itemCount,
                view = { view: View, position: Int ->
                    builder.invoke(view, position)
                }
            )
            this.layoutManager = getCustomLayoutManager()
        }
    }

    fun submitItems(itemCount: Int) {
        (recycler.adapter as? SimpleRecyclerAdapter)?.addItemCount(itemCount)
    }

    private fun getCustomLayoutManager(): RecyclerView.LayoutManager {
        val layoutType = attributes.getInt(R.styleable.simplerecyclerview_layout_manager, 0)
        val layoutOrientation = attributes.getInt(R.styleable.simplerecyclerview_orientation, 1)
        val spanCount = attributes.getInt(R.styleable.simplerecyclerview_span_count, 1)

        return when (layoutType) {
            1 -> GridLayoutManager(recycler.context, spanCount)
            2 -> StaggeredGridLayoutManager(spanCount, layoutOrientation)
            else -> LinearLayoutManager(recycler.context, layoutOrientation, false)
        }
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.simple_recycler_layout, this)

        recycler = findViewById(R.id.recycler)

        attributes = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.simplerecyclerview,
            0, 0
        )

        recycler.apply {
            layoutManager = getCustomLayoutManager()
        }
    }
}