package com.cosage.zzh.kotlin

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * Created by Zhengzhihui on 2017/5/15.
 */

abstract class EndlessRecyclerOnScrollListener(
    private val mLinearLayoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {
    var previousTotal = 0
    private var loading = true
    internal var firstVisibleItem: Int = 0
    internal var visibleItemCount: Int = 0
    internal var totalItemCount: Int = 0

    private var currentPage = 1
    var loaded=true


    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        visibleItemCount = recyclerView!!.childCount
        totalItemCount = mLinearLayoutManager.itemCount
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition()


        if (loading) {
            if (totalItemCount > previousTotal) {
                previousTotal = totalItemCount
                loading = false
            }
        }

        if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem) {
            if(loaded) {

                currentPage++
                onLoadMore(currentPage)
                loading = true
             }else{

            }
        }
    }

    fun clear() {
        currentPage = 1
        previousTotal = 0
        loaded=true
    }

    abstract fun onLoadMore(currentPage: Int)
}
