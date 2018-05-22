package com.hevttc.jdr.interiew.bean;

import java.io.Serializable;

/**
 * Created by hegeyang on 2018/5/22.
 */

public class UserSettingBean implements Serializable {

    /**
     * userId : 5
     * examNum : 5
     * examType : 0
     */

    private int userId;
    private int examNum;
    private String examType;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getExamNum() {
        return examNum;
    }

    public void setExamNum(int examNum) {
        this.examNum = examNum;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }
}
