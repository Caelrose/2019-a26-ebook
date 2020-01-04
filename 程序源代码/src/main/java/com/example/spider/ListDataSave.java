package com.example.spider;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class ListDataSave {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public ListDataSave(Context mContext, String preferenceName) {
        preferences = mContext.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    /**
     * 保存List
     * @param tag
     * @param datalist
     */
    public  void setDataList(String tag, List<BookBean> datalist) {
        if (null == datalist || datalist.size() <= 0)
            return;

        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);

        editor.putString(tag, strJson);
        editor.commit();

    }

    /**
     * 获取List
     * @param tag
     * @return
     */
    public  List<BookBean> getDataList(String tag) {
        List<BookBean> datalist=new ArrayList<BookBean>();
        String strJson = preferences.getString(tag, null);
        if (null == strJson) {
            return null;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<BookBean>>() {
        }.getType());
        return datalist;

    }
}
