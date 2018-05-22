package com.hevttc.jdr.interiew.bean;

import java.util.List;

/**
 * Created by hegeyang on 2018/5/22.
 */

public class MySignListBean {

    /**
     * uid : 1
     * content : 我在打卡了1天，学习了0道题
     * createTime : 2017-12-18 16:40:00
     * talkList : [{"nickName":"小九","headImg":null,"text":"好开心a","time":"154天之前","id":5},{"nickName":"小九","headImg":null,"text":"好开心","time":"154天之前","id":4}]
     */

    private int uid;
    private String content;
    private String createTime;
    private List<TalkListBean> talkList;

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

    public List<TalkListBean> getTalkList() {
        return talkList;
    }

    public void setTalkList(List<TalkListBean> talkList) {
        this.talkList = talkList;
    }

    public static class TalkListBean {
        /**
         * nickName : 小九
         * headImg : null
         * text : 好开心a
         * time : 154天之前
         * id : 5
         */

        private String nickName;
        private Object headImg;
        private String text;
        private String time;
        private int id;

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

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
