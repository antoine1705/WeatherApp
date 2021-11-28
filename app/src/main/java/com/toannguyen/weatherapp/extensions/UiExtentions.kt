package com.toannguyen.weatherapp.extensions

import androidx.recyclerview.widget.RecyclerView
import com.toannguyen.weatherapp.utils.EndlessRecyclerOnScrollListener

fun RecyclerView.onLoadMore(block: () -> Unit) {
    this.clearOnScrollListeners()
    this.addOnScrollListener(object: EndlessRecyclerOnScrollListener(){
        override fun onLoadMore() {
            block.invoke()
        }

    })
}