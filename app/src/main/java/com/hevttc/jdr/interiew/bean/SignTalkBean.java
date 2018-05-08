package com.hevttc.jdr.interiew.bean;

/**
 * Created by hegeyang on 2018/5/8.
 */

public class SignTalkBean {

    /**
     * talkId : 1
     * nickName : 小九
     * headImg : null
     * talkText : 我在打卡了1天，学习了0道题
     * createTime : -1小时之前
     * status : 1
     */

    private int talkId;
    private String nickName;
    private Object headImg;
    private String talkText;
    private String createTime;
    private int status;

    public int getTalkId() {
        return talkId;
    }

    public void setTalkId(int talkId) {
        this.talkId = talkId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Object getHeadImg() {
        return headImg;
    }

    public void setHeadImg(Object headImg) {
        this.headImg = headImg;
    }

    public String getTalkText() {
        return talkText;
    }

    public void setTalkText(String talkText) {
        this.talkText = talkText;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
