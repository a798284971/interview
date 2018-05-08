package com.hevttc.jdr.interiew.bean;

import java.util.List;

/**
 * Created by hegeyang on 2018/4/28.
 */

public class WrongListBean {


    /**
     * id : 10
     * title : 概率统计
     * dataList : [{"id":4,"userId":5,"questionId":6,"createtime":"2018-05-02 17:42:38","type":0,"superioe":10,"title":"三次射击能射中最少一次的概率为095，请问一次射击能中的概率是多少？","wrongAnswer":"1"}]
     */

    private int id;
    private String title;
    private List<DataListBean> dataList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataListBean {
        /**
         * id : 4
         * userId : 5
         * questionId : 6
         * createtime : 2018-05-02 17:42:38
         * type : 0
         * superioe : 10
         * title : 三次射击能射中最少一次的概率为095，请问一次射击能中的概率是多少？
         * wrongAnswer : 1
         */

        private int id;
        private int userId;
        private int questionId;
        private String createtime;
        private int type;
        private int superioe;
        private String title;
        private String wrongAnswer;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getQuestionId() {
            return questionId;
        }

        public void setQuestionId(int questionId) {
            this.questionId = questionId;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getSuperioe() {
            return superioe;
        }

        public void setSuperioe(int superioe) {
            this.superioe = superioe;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getWrongAnswer() {
            return wrongAnswer;
        }

        public void setWrongAnswer(String wrongAnswer) {
            this.wrongAnswer = wrongAnswer;
        }
    }
}
