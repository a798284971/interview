package com.hevttc.jdr.interiew.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hegeyang on 2018/5/2.
 */

public class ExeCommitBean implements Serializable{
    public HashMap<Integer,String> answer;
    public BaseBean<List<ExerciseQuestionBean>> baseBean;
    public int time;
    public String title;
}
