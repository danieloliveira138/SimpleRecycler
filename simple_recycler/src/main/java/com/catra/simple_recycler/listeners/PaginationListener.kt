package com.catra.simple_recycler.listeners

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PaginationListener(
    private val layoutManager: LinearLayoutManager,
    private val pageSize: Int,
    private val isLoading: () -> Boolean,
    private val totalItemsLoaded: () -> Boolean,
    private val loadMoreItems: (Int) -> Unit
) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItems = layoutManager.childCount
        val totalItems = layoutManager.itemCount
        val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

        if (!isLoading.invoke() && !totalItemsLoaded.invoke()) {
            if ((visibleItems + firstVisibleItem) >= totalItems &&
                firstVisibleItem >= 0 &&
                totalItems >= pageSize) {
                loadMoreItems.invoke(totalItems)
            }
        }
    }
}