package com.hevttc.jdr.interiew.bean;

import java.util.List;

/**
 * Created by hegeyang on 2018/4/28.
 */

public class WrongListBean {

    /**
     * id : 7
     * title : 软件工程
     * dataList : [{"id":1,"userId":1,"questionId":1,"createtime":"2017-12-10 15:16:41","type":0,"superioe":7},{"id":2,"userId":1,"questionId":2,"createtime":"2017-12-10 15:22:28","type":0,"superioe":7}]
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
         * id : 1
         * userId : 1
         * questionId : 1
         * createtime : 2017-12-10 15:16:41
         * type : 0
         * superioe : 7
         */

        private int id;
        private int userId;
        private int questionId;
        private String createtime;
        private int type;
        private int superioe;

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
    }
}
