package com.cosage.zzh.kotlin

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.cosage.zzh.kotlin.R

import org.jetbrains.anko.find
import org.w3c.dom.Text

/**
 * Created by Zhengzhihui on 2017/5/19.
 */

 abstract class BaseDialog @JvmOverloads constructor(context:Context, textStr:String, cancelMode:Boolean=true, detail:String=""){

     init {
         val dialog = Dialog(context, R.style.AlertDialogStyle)
         //        //填充对话框的布局
         val inflate = LayoutInflater.from(context).inflate(R.layout.dialog_confirm_order, null)
         //        //初始化控件
         //将布局设置给Dialog
         dialog.setContentView(inflate)
             //获取当前Activity所在的窗体
        val dialogWindow = dialog.window
        //设置Dialog从窗体从中间弹出
        dialogWindow!!.setGravity(Gravity.CENTER)
        val lLayout_bg = inflate.find<LinearLayout>(id = R.id.lLayout_bg)
        val display = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        lLayout_bg.setLayoutParams(FrameLayout.LayoutParams((display.width * 0.85).toInt(), LinearLayout.LayoutParams.WRAP_CONTENT))
        dialog.setCancelable(true)
        dialog.show()//显示对话框
         if(detail!=""){
             inflate.findViewById<View>(R.id.dialog_detail).visibility=View.VISIBLE
             (inflate.findViewById<View>(R.id.dialog_tv_detail) as TextView).text=detail
         }
        val btn_cancel = inflate.findViewById<View>(R.id.btn_neg)
         (inflate.findViewById<View>(R.id.confirm_order_tv_title) as TextView).text = textStr
         if(cancelMode) {
             btn_cancel.visibility = View.VISIBLE
             btn_cancel.setOnClickListener {
                 dialog.dismiss()
                 onCancel()
             }
         }
        inflate.findViewById<View>(R.id.btn_pos).setOnClickListener {
            dialog.dismiss()
             onConfirm()
        }
     }
    abstract  fun onConfirm()
    abstract  fun onCancel()
}
