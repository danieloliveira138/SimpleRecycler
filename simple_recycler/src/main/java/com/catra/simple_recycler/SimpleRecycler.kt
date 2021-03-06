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
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.catra.simple_recycler.listeners.PaginationListener

private const val UNDEFINED = -1

class SimpleRecycler(
    context: Context,
    attrs: AttributeSet?
) : ConstraintLayout(context, attrs) {

    private val recycler: RecyclerView
    private val swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var simpleRecyclerAdapter: SimpleRecyclerAdapter
    private val itemLayout: Int?
    private val attributes: TypedArray
    private var isLoading = false
        set(value) {
                field = value
                (recycler.adapter as? SimpleRecyclerAdapter)?.loading = field
        }
    private var totalItemsLoaded = false
    private var isRefreshListener = false

    fun listBuilder(
        itemCount: Int,
        holderLayout: Int? = null,
        refreshListener: (() -> Unit)? = null,
        builder: (View, Int) -> Unit
    ) {
        simpleRecyclerAdapter = SimpleRecyclerAdapter(
            layoutRes = holderLayout ?: itemLayout ?: return,
            itemCount = itemCount,
            view = { view: View, position: Int ->
                builder.invoke(view, position)
            }
        )

        recycler.apply {
            adapter = simpleRecyclerAdapter

            this.layoutManager = getCustomLayoutManager()

            refreshListener?.let { onRefreshRecycler(it) }
        }
    }

    fun onPaginationListener(
        pageSize: Int,
        func: (Int) -> Unit
    ) = with(recycler) {

        addOnScrollListener(PaginationListener(
            this.layoutManager as LinearLayoutManager,
            pageSize,
            isLoading = {
                isLoading
            },
            totalItemsLoaded = {
                totalItemsLoaded
            },
            loadMoreItems = {
                post {
                    isLoading = true
                    func.invoke(it)
                }
            }
        ))
    }

    fun onRefreshRecycler(func: () -> Unit) {
        swipeRefreshLayout.apply {
            isEnabled = true
            setOnRefreshListener(func)
        }
    }

    fun isRefreshLoading(enable: Boolean) {
        swipeRefreshLayout.isRefreshing = enable
    }

    fun submitItems(itemCount: Int) {
        isLoading = false
        (recycler.adapter as? SimpleRecyclerAdapter)?.addItemCount(itemCount)
    }

    private fun getCustomLayoutManager(): LayoutManager {
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

        swipeRefreshLayout = findViewById(R.id.swipeRefresh)

        attributes = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.simplerecyclerview,
            0, 0
        )

        itemLayout = attributes
            .getResourceId(
                R.styleable.simplerecyclerview_item_layout,
                UNDEFINED
            ).takeIf {
                it != UNDEFINED
            }

        recycler.apply {
            layoutManager = getCustomLayoutManager()
        }
    }
}
