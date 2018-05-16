package com.hevttc.jdr.interiew.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by hegeyang on 2017/12/22.
 */

public class MessageBean implements Serializable,Parcelable{

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

    protected MessageBean(Parcel in) {
        id = in.readInt();
        title = in.readString();
        content = in.readString();
        createTime = in.readString();
    }

    public static final Creator<MessageBean> CREATOR = new Creator<MessageBean>() {
        @Override
        public MessageBean createFromParcel(Parcel in) {
            return new MessageBean(in);
        }

        @Override
        public MessageBean[] newArray(int size) {
            return new MessageBean[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(createTime);
    }
}
