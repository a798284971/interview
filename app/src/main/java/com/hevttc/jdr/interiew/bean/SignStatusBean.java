package com.hevttc.jdr.interiew.bean;

/**
 * Created by hegeyang on 2018/4/10.
 */

public class SignStatusBean {

    /**
     * userId : 5
     * questionNum : 0
     * signNum : 0
     * studyNum : 1
     * timeStamp : 打卡
     */

    private int userId;
    private int questionNum;
    private int signNum;
    private int studyNum;
    private String timeStamp;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(int questionNum) {
        this.questionNum = questionNum;
    }

    public int getSignNum() {
        return signNum;
    }

    public void setSignNum(int signNum) {
        this.signNum = signNum;
    }

    public int getStudyNum() {
        return studyNum;
    }

    public void setStudyNum(int studyNum) {
        this.studyNum = studyNum;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
