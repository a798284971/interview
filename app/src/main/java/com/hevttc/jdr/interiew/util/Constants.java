package com.hevttc.jdr.interiew.util;

/**
 * Created by hegeyang on 2017/12/21.
 */

public class Constants {
    /*API通用头*/
    public static final String SERVER_HEAD = "http://10.154.106.7:8082/";

    /*App相关接口*/
    public static final String API_APP_HEAD = SERVER_HEAD+"app/";
    //轮播图
    public static final String API_LUNBO= API_APP_HEAD+"getLunbo";
    //轮询消息
    public static final String API_MESSAGE = API_APP_HEAD+"getMessage";


    /*用户相关接口*/
    public static final String API_USER_HEAD = SERVER_HEAD+"user/";
    //发送短信验证码
    public static final String API_SENDMESSAGE= API_USER_HEAD+"sendMsg";
    //注册用户
    public static final String API_REGISTER = API_USER_HEAD+"regist";
    //用户登录
    public static final String API_LOGININ =API_USER_HEAD+"login";
    //用户状态
    public static final String API_SIGN_STATUS = API_USER_HEAD+"getSignStatus";
    //获取用户测试状态
    public static final String API_TEST_STATUS = API_USER_HEAD+"getTestStatus";




    /*题目相关*/
    public static final String API_EXECRISE_HEAD = SERVER_HEAD+"exercise/";
    //获取列表
    public static final String API_EXECRISE_LIST = API_EXECRISE_HEAD+"getExerciseListByUid";


    //*用户常量*/
    public static final String SP_LOGIN = "login";
}

