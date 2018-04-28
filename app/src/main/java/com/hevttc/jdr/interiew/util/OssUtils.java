package com.hevttc.jdr.interiew.util;

import android.content.Context;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;

import java.io.File;
import java.util.HashMap;

/**
 * Created by hegeyang on 2017/11/15.
 */

public class OssUtils {

    private static OSSClient oss;
    private static PutObjectRequest put;

    public static OSSClient initOss(Context context){
       if(oss==null){
           // ACCESS_ID,ACCESS_KEY是在阿里云申请的
           String id = SPUtils.getString(context,Constants.SP_ACCESS_ID,"");
           String pass = SPUtils.getString(context,Constants.SP_ACCESS_PAS,"");
           OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(id,pass);
           ClientConfiguration conf = new ClientConfiguration();
           conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
           conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
           conf.setMaxConcurrentRequest(8); // 最大并发请求数，默认5个
           conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次

           // oss为全局变量，OSS_ENDPOINT是一个OSS区域地址
           oss = new OSSClient(context, Constants.END_POINT, credentialProvider, conf);
       }
        return oss;
    }
    //上传图片
    public static PutObjectRequest putImage(File file,String ossPath){
            ObjectMetadata objectMeta = new ObjectMetadata();
            //objectMeta.setContentType("image/jpeg");
            LogUtil.e("============"+file.getPath()+"========="+file.getName());
            put = new PutObjectRequest("jdr-interview", ossPath+file.getName(),file.getPath());
            put.setMetadata(objectMeta);
            put.setCallbackParam(new HashMap<String, String>() {
                {
                    //put("callbackUrl", "110.75.82.106/callback");
                    put("callbackHost", "oss-cn-hangzhou.aliyuncs.com");
                    put("callbackBodyType", "application/json");
                    put("callbackBody", "{\"mimeType\":${mimeType},\"size\":${size}}");
                }
            });


        return put;
    }
}
