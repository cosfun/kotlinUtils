package com.cosage.zzh.kotlin

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by Zhengzhihui on 2017/4/14.
 */

 abstract class BaseAdapter<T>(protected var context: Context, protected var mLayoutId: Int, var infoList: ArrayList<T>) : RecyclerView.Adapter<ViewHolder>() {
    protected var inflater: LayoutInflater? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder.get(context, parent, mLayoutId)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        convert(holder, position, infoList[position])
    }

    abstract fun convert(holder: ViewHolder, position: Int, t: T)
    override fun getItemCount(): Int {
        return infoList.size
    }
    operator fun minus(oo:Int){
        this.notifyItemRemoved(oo)
        infoList.removeAt(oo)
        this.notifyItemRangeChanged(oo,infoList.size-oo)
    }
}
