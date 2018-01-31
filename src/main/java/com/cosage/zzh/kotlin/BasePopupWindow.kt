package com.cosage.zzh.kotlin

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import android.widget.RelativeLayout

/**
 * Created by Zhengzhihui on 2017/5/18.
 */

abstract class BasePopupWindow(
        context: Context,
        layout: Int,
        width: Int = RelativeLayout.LayoutParams.WRAP_CONTENT,
        height: Int = RelativeLayout.LayoutParams.WRAP_CONTENT,
        animationStyle: Int = 0,
        color: Int =0x00000000
)
    : PopupWindow() {
    init {
        val contentView = LayoutInflater.from(context).inflate(layout, null)
        this.contentView = contentView
        this.width = width
        this.height = height
        // 设置弹出窗体可点击
        this.isFocusable = true
        this.isOutsideTouchable = false
        // 实例化一个ColorDrawable颜色为半透明
        val dw = ColorDrawable(color)
        // 设置弹出窗体的背景
        this.initView(contentView)
        this.setBackgroundDrawable(dw)
        // 设置弹出窗体显示时的动画，从底部向上弹出
        if(animationStyle!=-1) {
            when(animationStyle) {
              0->this.animationStyle = R.style.xunleiDialogAnimation
              1->  this.animationStyle = R.style.popAnimation
            }
        }
    }
    val thisContext:Context=context
    override fun dismiss() {
        super.dismiss()
        setBackgroundAlpha( thisContext, 1f)
    }
    fun showBg(parent:View, gravity: Int,x:Int,y:Int){
        showAtLocation(parent, gravity, x, y)
        setBackgroundAlpha(thisContext,0.5f)
    }
    abstract fun initView(view: View)
}
