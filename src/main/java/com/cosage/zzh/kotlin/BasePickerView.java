package com.cosage.zzh.kotlin;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.OnDismissListener;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhengzhihui on 2017/7/7.
 */

public   class BasePickerView {
    //normal  init setData start show
    //three   initData show
     Context context;
    List<String> data;

    public ArrayList<JsonBean> getData1() {
        return data1;
    }

    ArrayList<JsonBean> data1;
    ArrayList<ArrayList<String>> data2 ;
    ArrayList<ArrayList<ArrayList<String>>> data3 ;
    OptionsPickerView pvOptions;
    public static final int MSG_LOAD_DATA = 0x0001;
    public static final int MSG_LOAD_SUCCESS = 0x0002;
    public static final int MSG_LOAD_FAILED = 0x0003;
    public Thread mmthread;

    public boolean isLoaded() {
        return isLoaded;
    }

    public boolean isLoaded = false;
    private boolean isConfirm=false;
    PickerListener pickerListener=null;
    public BasePickerView initData(final PickerListener pickerListener){
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        data2 = new ArrayList<>();
        data3 = new ArrayList<>();
        this.pickerListener=pickerListener;
        return this;
    }
    public BasePickerView init(Context context){
         this.context=context;
         return this;
     }
    public BasePickerView setData(List<String> data){
        this.data=data;
        return this;
    }



    public interface PickerListener{
         void onPicked(int options,int options2,int options3);
         void onCancel();
    }

    public BasePickerView start(final PickerListener pickerListener) {
         pvOptions = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                 //onOptionsSelected(options1);
                isConfirm=true;
                pickerListener.onPicked(options1,0,0);
            }
        }).build();
        pvOptions.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(Object o) {
                if(!isConfirm){
                    pickerListener.onCancel();
                }
                isConfirm=false;
            }
        });
        pvOptions.setPicker(data);
        isLoaded = true;
        return this;
    }

    public BasePickerView startThree(final PickerListener pickerListener) {
        pvOptions = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //onOptionsSelected(options1);
                isConfirm=true;
                pickerListener.onPicked(options1,option2,options3);
            }
        }).build();
        pvOptions.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(Object o) {
                if(!isConfirm){
                    pickerListener.onCancel();
                }
                isConfirm=false;
            }
        });
        pvOptions.setPicker(data1,data2,data3);
        isLoaded = true;
        return this;
    }

    public void show(){
        if(isLoaded) {
            pvOptions.show();
        }else{
            Toast.makeText(context, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
        }
    }
   // protected abstract void onOptionsSelected(int options1);
    public void onDestroy(){
        context=null;
        data=null;
        pvOptions=null;
    }

    protected void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(context,"province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = (ArrayList<JsonBean>) JSONArray.parseArray(JsonData,JsonBean.class);// 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        data1 = jsonBean;

        for (int i=0;i<jsonBean.size();i++){//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c=0; c<jsonBean.get(i).getCity().size(); c++){//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCity().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCity().get(c).getArea() == null
                        ||jsonBean.get(i).getCity().get(c).getArea().size()==0) {
                    City_AreaList.add("");
                }else {

                    for (int d=0; d < jsonBean.get(i).getCity().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCity().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            data2.add(CityList);

            /**
             * 添加地区数据
             */
            data3.add(Province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (mmthread==null){//如果已创建就不再重新创建子线程了
                        Log.e("huang","省市区开始解析数据");
//                        Toast.makeText(applicationContext,"开始解析数据", Toast.LENGTH_SHORT).show();
                        mmthread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 写子线程中的操作,解析省市区数据
                                initJsonData();
                            }
                        });
                        mmthread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    Log.e("huang","省市区解析数据成功");
//                    Toast.makeText(applicationContext,"解析数据成功", Toast.LENGTH_SHORT).show();
                    startThree(pickerListener);
                    break;

                case MSG_LOAD_FAILED:
                    Log.e("huang","省市区解析数据失败");
//                    Toast.makeText(applicationContext,"解析数据失败", Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };

}
