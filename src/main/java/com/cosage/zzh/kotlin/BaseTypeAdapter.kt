package com.cosage.zzh.kotlin

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by Zhengzhihui on 2017/4/17.
 */

abstract class BaseTypeAdapter<T>(val context: Context,val mLayoutList: IntArray,val infoList: ArrayList<T>) : RecyclerView.Adapter<ViewHolder>() {
    constructor( context: Context,   mLayoutList: IntArray,   infoList: ArrayList<T>,mode:Int):this(context,mLayoutList,infoList){
        model=mode
    }
    protected var inflater: LayoutInflater? = null
    var mTypeState:Array<Int>?=null
    var model:Int = 0;
    val typeHash=HashMap<Int,Int>();
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
          val viewHolder = ViewHolder.get(context, parent, mLayoutList[viewType])
           return viewHolder
    }
    override fun getItemViewType(position: Int): Int {
        if(model==0) {
            if (mTypeState!!.size > 0 && position < mTypeState!!.size)
                return mTypeState!![position]
            return 0
        }else{
            val type=getBaseItemViewType(position)
            typeHash.put(position,type)
            return  type
        }
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(model==0) {
            if (mTypeState!!.size > 0 && mTypeState!!.size > position)
                convert(holder, position, infoList[position], mTypeState!![position])
            else
                convert(holder, position, infoList[position], 0)
        }else{
            convert(holder, position, infoList[position],typeHash.get(position)?:0)
        }
    }

    abstract fun convert(holder: ViewHolder, position: Int, t: T, type:Int)
    abstract fun getBaseItemViewType(position: Int):Int
    override fun getItemCount(): Int {
        return infoList.size
    }
    operator fun minus(oo:Int){
        this.notifyItemRemoved(oo)
        infoList.removeAt(oo)
        this.notifyItemRangeChanged(oo,infoList.size-oo)
    }
}
