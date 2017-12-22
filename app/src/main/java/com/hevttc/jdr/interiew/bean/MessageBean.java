package com.hevttc.jdr.interiew.bean;

import java.io.Serializable;

/**
 * Created by hegeyang on 2017/12/22.
 */

public class MessageBean implements Serializable{

    /**
     * id : 1
     * title : 测试
     * content : www.baidu.com
     * createTime : 2017-12-18 09:30:00
     */

    private int id;
    private String title;
    private String content;
    private String createTime;

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
