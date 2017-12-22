package com.hevttc.jdr.interiew.util;

/**
 * Created by hegeyang on 2017/12/21.
 */

public class Constants {
    /*API通用头*/
    public static final String SERVER_HEAD = "http://59.110.238.249/interview/";

    /*App相关接口*/
    public static final String API_APP_HEAD = SERVER_HEAD+"app/";
    //轮播图
    public static final String API_LUNBO= API_APP_HEAD+"getLunbo";
    //轮询消息
    public static final String API_MESSAGE = API_APP_HEAD+"getMessage";
    //用户状态



    /*用户相关接口*/
    public static final String API_USER_HEAD = SERVER_HEAD+"user/";
    //发送短信验证码
    public static final String API_SENDMESSAGE= API_USER_HEAD+"sendMsg";
    //注册用户
    public static final String API_REGISTER = API_USER_HEAD+"regist";
    //用户登录
    public static final String API_LOGININ =API_USER_HEAD+"login";




    //*用户常量*/
    public static final String SP_LOGIN = "login";
}

