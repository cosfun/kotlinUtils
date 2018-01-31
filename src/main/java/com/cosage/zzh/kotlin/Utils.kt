package com.cosage.zzh.kotlin

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import java.math.BigDecimal

import kotlin.collections.ArrayList

/**
 * Created by Zhengzhihui on 2017/4/6.
 */
fun <T,R> T.log(message:R) {
    android.util.Log.i("test",message.toString())
}
fun View.clickable(boolean: Boolean) {

}
fun TextView.setUse(string:String,visibility: Int,done:()->Unit) {
    text=string
    (this.parent as RelativeLayout).visibility=visibility;
    setOnClickListener {
        done()
    }
}
fun <T> ArrayList<T>.setDefualt(t:T,size:Int):ArrayList<T>{
    clear()
    for(i in 0..size-1)
        add(t)
    return this
}
fun <T> BaseTypeAdapter<T>.set(arr:Array<Int>): BaseTypeAdapter<T> {
    mTypeState=arr
    return this
}

fun  Int.s(value:String="-2"):String{
    if(this.toString()==value)
        return ""
    return this.toString()
}

fun <T:Any> magic(infoList: ArrayList<T>,model:Class<*>,magicType:Int=-1):ArrayList<Any>{
    var count=magicType
    count++
    val anser=ArrayList<Any>()
    if(0==infoList.size)
        return anser
    magicData<T> {
        this.magicType=count
        saveData=anser
        Nod_Y_Lle_Delfrydol{
            this initData infoList
        }
        Le_Cheile_Beidh_Muid{
            this initData model
        }
    }
    return anser
}
class MagicData<T:Any> {
    var samClass:Class<out T>?=null
    var saveData:ArrayList<Any>?=null
    var innerName=""
    var haveClass=false
    var methosName=ArrayList<String>()
    var methosParmsType=ArrayList<Class<*>>()
    var methosClass=""
    var infoList: ArrayList<T>?=null
    var model:Class<*>?=null
    var magicType=-1
    fun Le_Cheile_Beidh_Muid(block: MagicData<T>.()->Unit){
        block()
        infoList?.forEach {
            val newModel=model!!.newInstance()
            methosName.forEachIndexed { index, s ->
                model!!.getMethod(s,methosParmsType[index]).invoke(newModel,samClass?.getMethod(s.replace("set","get"))?.invoke(it))
            }
            model!!.getMethod("setMagicType",methosParmsType[0]).invoke(newModel,magicType.toString())
            saveData?.add(newModel)
            if(haveClass) {
                val me=samClass?.getMethod(methosClass)?.invoke(it) as ArrayList<Any>
                if (me.size>0){
                    saveData?.addAll(magic(me,model!!,magicType))
                }
            }
        }
    }

    infix fun initData(model:Class<*>){
        this.model=model
    }
    fun Pwysig_Iw_Pentyrru(){
        for(i in samClass!!.declaredMethods){
            for(j in i.genericParameterTypes){
                // println("j="+j.toString()+",innerName="+innerName+",return="+j.toString().contains("$"))
                // if(i.name.startsWith("set")&&!j.toString().contains(innerName)){
                if(i.name.startsWith("set")&&!j.toString().contains("$")){
                    methosName.add(i.name)
                    //log("name1="+i.parameterTypes[0].canonicalName+"name2="+i.parameterTypes[0].name)
                    methosParmsType.add(i.parameterTypes[0])

                }
            }
            // if(i.name.startsWith("get")&&i.genericReturnType.toString().contains(innerName)){
            if(haveClass&&i.name.startsWith("get")&&i.genericReturnType.toString().contains("$")){
                methosClass=i.name
            }
        }
    }
    infix fun initData(data: ArrayList<T>){
        infoList=data
        samClass  =  data[0]::class.java
    }
    fun Nod_Y_Lle_Delfrydol(block: MagicData<T>.()->Unit){
        block()
        innerName=if(samClass!!.declaredClasses.size>0) samClass!!.declaredClasses!![0].name else "-1"
        haveClass=!innerName.equals("-1")
        Pwysig_Iw_Pentyrru()
    }
}
fun <T:Any> magicData(block: MagicData<T>.()->Unit): MagicData<T> {
    val new= MagicData<T>()
    block(new)
    return new
}
fun String.toF(num:Int):String {
    return String.format("%."+num+"f",this.toDouble())
}

fun String.subZeroAndDot(): String {
    var s=this
    if (s.indexOf(".") > 0) {
        s = s.replace("0+?$".toRegex(), "")//去掉多余的0
        s = s.replace("[.]$".toRegex(), "")//如最后一位是.则去掉
    }
    return s
}
fun ArrayList<String>.toString(a:String):String{
    var anser=""
    forEachIndexed { index, s ->
        anser+=s
        if(index!=size-1)
            anser+=a
    }
    return anser
}

fun String.toNumber():String{
    var anser=""
    this.forEach {
        if(it in '0'..'9')
            anser+=it
    }
    return  anser
}

fun setBackgroundAlpha(mContext: Context, bgAlpha: Float) {
    val lp = (mContext as Activity).window
            .attributes
    lp.alpha = bgAlpha
    (mContext as Activity).window.attributes = lp
}
fun BigDecimal.toF():String{
    if(BigDecimal(this.toInt()).compareTo(this)==0){
        return this.toInt().toString();
    }
    return this.toString();
}