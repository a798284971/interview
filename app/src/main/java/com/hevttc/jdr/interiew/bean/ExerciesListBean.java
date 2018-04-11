package com.hevttc.jdr.interiew.bean;

import java.util.List;

/**
 * Created by hegeyang on 2018/4/11.
 */

public class ExerciesListBean {


    /**
     * id : 1
     * title : 软件开发
     * dataList : [{"id":7,"title":"软件工程","superioe":1,"questionNum":4,"isStudyNum":0,"rightRate":0},{"id":8,"title":"软件测试","superioe":1,"questionNum":3,"isStudyNum":0,"rightRate":0}]
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
         * id : 7
         * title : 软件工程
         * superioe : 1
         * questionNum : 4
         * isStudyNum : 0
         * rightRate : 0
         */

        private int id;
        private String title;
        private int superioe;
        private int questionNum;
        private int isStudyNum;
        private int rightRate;

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

        public int getSuperioe() {
            return superioe;
        }

        public void setSuperioe(int superioe) {
            this.superioe = superioe;
        }

        public int getQuestionNum() {
            return questionNum;
        }

        public void setQuestionNum(int questionNum) {
            this.questionNum = questionNum;
        }

        public int getIsStudyNum() {
            return isStudyNum;
        }

        public void setIsStudyNum(int isStudyNum) {
            this.isStudyNum = isStudyNum;
        }

        public int getRightRate() {
            return rightRate;
        }

        public void setRightRate(int rightRate) {
            this.rightRate = rightRate;
        }
    }
}
