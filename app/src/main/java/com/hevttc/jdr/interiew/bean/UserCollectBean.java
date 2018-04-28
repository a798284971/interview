package com.hevttc.jdr.interiew.bean;

/**
 * Created by hegeyang on 2018/4/28.
 */

public class UserCollectBean {

    /**
     * id : 1
     * createTime : 2017-12-09 16:15:23
     * title : 汽车有一个发动机。汽车和发动机之间属于（  ）关系
     * question : {"id":1,"superioe":7,"question":"汽车有一个发动机。汽车和发动机之间属于（  ）关系","a":"一般具体","b":"主从关系","c":"分类关系","d":"整体部分","haspic":0,"pic":null,"type":0}
     */

    private int id;
    private String createTime;
    private String title;
    private QuestionBean question;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public QuestionBean getQuestion() {
        return question;
    }

    public void setQuestion(QuestionBean question) {
        this.question = question;
    }

    public static class QuestionBean {
        /**
         * id : 1
         * superioe : 7
         * question : 汽车有一个发动机。汽车和发动机之间属于（  ）关系
         * a : 一般具体
         * b : 主从关系
         * c : 分类关系
         * d : 整体部分
         * haspic : 0
         * pic : null
         * type : 0
         */

        private int id;
        private int superioe;
        private String question;
        private String a;
        private String b;
        private String c;
        private String d;
        private int haspic;
        private Object pic;
        private int type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSuperioe() {
            return superioe;
        }

        public void setSuperioe(int superioe) {
            this.superioe = superioe;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

        public String getC() {
            return c;
        }

        public void setC(String c) {
            this.c = c;
        }

        public String getD() {
            return d;
        }

        public void setD(String d) {
            this.d = d;
        }

        public int getHaspic() {
            return haspic;
        }

        public void setHaspic(int haspic) {
            this.haspic = haspic;
        }

        public Object getPic() {
            return pic;
        }

        public void setPic(Object pic) {
            this.pic = pic;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
