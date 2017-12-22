package com.hevttc.jdr.interiew.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hevttc.jdr.interiew.bean.BaseBean;
import com.hevttc.jdr.interiew.bean.UserInfoBean;

import java.lang.reflect.Type;


public final class SPUtils {

    private final static String name = "config";
    private final static int mode = Context.MODE_PRIVATE;

    /**
     * 保存首选项
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(name, mode);
        Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    public static void saveInt(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(name, mode);
        Editor edit = sp.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    public static void saveString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(name, mode);
        Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }


    /**
     * 获取首选项
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        SharedPreferences sp = context.getSharedPreferences(name, mode);
        return sp.getBoolean(key, defValue);
    }

    public static int getInt(Context context, String key, int defValue) {
        SharedPreferences sp = context.getSharedPreferences(name, mode);
        return sp.getInt(key, defValue);
    }

    public static String getString(Context context, String key, String defValue) {
        SharedPreferences sp = context.getSharedPreferences(name, mode);
        return sp.getString(key, defValue);
    }


    private static final String SPNAME = "ruiyihong";
    private static SharedPreferences sp;

    public static void putBoolean(String key, boolean value, Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(String key, Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
        }
        boolean b = sp.getBoolean(key, false);
        return b;
    }
    public UserInfoBean getSignInfo(Context context){
        String user = getString(context, Constants.SP_LOGIN, "");
        if (!user.equals("")) {
            Type type = new TypeToken<BaseBean<UserInfoBean>>() {
            }.getType();
            BaseBean<UserInfoBean> baseBean = new Gson().fromJson(user, type);

            return baseBean.getData();
        } else {
            return null;
        }
    }
}
