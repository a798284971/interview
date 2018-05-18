package com.hevttc.jdr.interiew.bean;

import java.util.List;

/**
 * Created by Administrator on 2018-5-17.
 */

public class CommentListBean {

    /**
     * code : 200
     * msg : 请求成功
     * success : true
     * data : [{"nickName":"小九","headImg":null,"text":"aaaa","time":"5小时之前","id":8},{"nickName":"毒药","headImg":"https://jdr-interview.oss-cn-beijing.aliyuncs.com/headimg/1525269264149.png","text":"hgy","time":"5小时之前","id":7},{"nickName":"毒药","headImg":"https://jdr-interview.oss-cn-beijing.aliyuncs.com/headimg/1525269264149.png","text":"xiaojiu","time":"5小时之前","id":6},{"nickName":"小九","headImg":null,"text":"好开心a","time":"149天之前","id":5},{"nickName":"小九","headImg":null,"text":"好开心","time":"149天之前","id":4}]
     */

    private int code;
    private String msg;
    private boolean success;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * nickName : 小九
         * headImg : null
         * text : aaaa
         * time : 5小时之前
         * id : 8
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
