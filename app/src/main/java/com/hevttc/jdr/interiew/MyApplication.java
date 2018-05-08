package com.hevttc.jdr.interiew;

import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;


import com.lzy.okgo.OkGo;
import com.mob.MobApplication;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Admin on 2017/11/5.
 */

public class MyApplication extends MobApplication {

    public static Class<?> next = null;
    public static Bundle nextBundle = null;


    private static MyApplication SINSTANCE;

    public static MyApplication getMyApplicaiton() {
        return SINSTANCE;
    }



    @Override
    public void onCreate() {
        super.onCreate();
        SINSTANCE = this;

        initOkGo();
//
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }
    }

    private void initOkGo() {
        OkGo.getInstance().init(this);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(3000, TimeUnit.SECONDS);
        OkGo.getInstance().setOkHttpClient(builder.build());
    }


}
