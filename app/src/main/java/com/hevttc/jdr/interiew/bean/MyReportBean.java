package com.hevttc.jdr.interiew.bean;

/**
 * Created by hegeyang on 2018/5/21.
 */

public class MyReportBean {

    /**
     * id : 1
     * uid : 5
     * content : 测试
     * createTime : 2018-05-16 15:45:30
     */

    private int id;
    private int uid;
    private String content;
    private String createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
