package com.hevttc.jdr.interiew.util;

/**
 * Created by hegeyang on 2017/12/21.
 */

public class Constants {
    /*API通用头*/
//    public static final String SERVER_HEAD = "http://10.154.104.191:8082/";
    public static final String SERVER_HEAD = "http://39.105.89.248:8080/interview/";

    /*App相关接口*/
    public static final String API_APP_HEAD = SERVER_HEAD+"app/";
    //轮播图
    public static final String API_LUNBO= API_APP_HEAD+"getLunbo";
    //轮询消息
    public static final String API_MESSAGE = API_APP_HEAD+"getMessage";
    //签到消息列表
    public static final String API_SIGN_LIST = API_APP_HEAD+"getSignTalk";
    //点赞签到消息
    public static final String API_SIGN_FIGHT= API_APP_HEAD+"talkFighUp";
    //评论接口
    public static final String API_SIGN_COMMENT = API_APP_HEAD+"addTalk";
    //评论列表接口
    public static final String API_SIGN_COMMENT_LIST = API_APP_HEAD+"getUserTalkList";


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
    //修改用户信息接口
    public static final String API_CHANGE_INFO = API_USER_HEAD+"changeInfo";
    //获取oss信息
    public static final String API_GET_OSS = API_USER_HEAD+"getOssID";


    /*题目相关*/
    public static final String API_EXECRISE_HEAD = SERVER_HEAD+"exercise/";
    //获取列表
    public static final String API_EXECRISE_LIST = API_EXECRISE_HEAD+"getExerciseListByUid";
    //获取专项练习题目列表
    public static final String API_EXECRISE_PRACTICE = API_EXECRISE_HEAD+"getExamexercise";
    //检查答案
    public static final String API_EXECRISE_CHECK_ANSWER = API_EXECRISE_HEAD+"checkAnswer";
    //获取某个/些题目信息
    public static final String API_EXECRISE_GETSOMEQUESTION = API_EXECRISE_HEAD+"exercisebyId";
    //获取考试题目列表
    public static final String API_EXECRISE_GETEXEAM_QUESTION = API_EXECRISE_HEAD+"getExamQuestion";

    /*用户题目相关*/
    public static final String API_EXEC_USER_HEAD = SERVER_HEAD+"exerciseUser/";
    //获取用户错题列表
    public static final String API_EXEC_USER_WRONGLIST = API_EXEC_USER_HEAD+"getWrongList";
    //获取用户收藏列表
    public static final String API_EXEC_USER_COLLECT = API_EXEC_USER_HEAD+"getCollectList";
    //提交用户错题
    public static final String API_EXEC_USER_COMMIT_WRONG = API_EXEC_USER_HEAD+"commitWrong";


    //*用户常量*/
    public static final String SP_LOGIN = "login";
    //获取大学名字
    public static final String API_GET_UNIVISITY = "http://119.29.166.254:9090/api/university/getByUniversityName";


    /*常量*/
    public static final String SP_ACCESS_ID = "access";
    public static final String SP_ACCESS_PAS ="pas";
    public static final String END_POINT = "http://oss-cn-beijing.aliyuncs.com";
    public static final String SERVER_PHOTO_HEAD = "https://jdr-interview.oss-cn-beijing.aliyuncs.com/";

    public static final String REME_NAME = "username";
    public static final String REME_PASS = "password";
    public static final String REME_FIRST = "first_login";
}

