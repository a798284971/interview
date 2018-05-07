package com.hevttc.jdr.interiew.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hegeyang on 2018/5/2.
 */

public class CheckAnswerBean implements Parcelable {

    /**
     * questionId : 2
     * answer : 3
     * analysis : 结构化程序设计方法的主要原则可以概括为自顶向下、逐步求精、模块化及限制使用 goto语句，总的来说可使程序结构良好、易读、易理解、易维护。
     * trueRate : 0
     */

    private int questionId;
    private String answer;
    private String analysis;
    private int trueRate;
    private boolean collect;

    protected CheckAnswerBean(Parcel in) {
        questionId = in.readInt();
        answer = in.readString();
        analysis = in.readString();
        trueRate = in.readInt();
        collect = in.readByte() != 0;
    }

    public static final Creator<CheckAnswerBean> CREATOR = new Creator<CheckAnswerBean>() {
        @Override
        public CheckAnswerBean createFromParcel(Parcel in) {
            return new CheckAnswerBean(in);
        }

        @Override
        public CheckAnswerBean[] newArray(int size) {
            return new CheckAnswerBean[size];
        }
    };

    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public int getTrueRate() {
        return trueRate;
    }

    public void setTrueRate(int trueRate) {
        this.trueRate = trueRate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(questionId);
        dest.writeString(answer);
        dest.writeString(analysis);
        dest.writeInt(trueRate);
        dest.writeByte((byte) (collect ? 1 : 0));
    }
}
