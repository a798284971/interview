package com.hevttc.jdr.interiew.view.fragment.choose_fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.bean.BaseBean;
import com.hevttc.jdr.interiew.bean.CheckAnswerBean;
import com.hevttc.jdr.interiew.bean.ExerciseQuestionBean;
import com.hevttc.jdr.interiew.util.Constants;
import com.hevttc.jdr.interiew.view.activity.ExerciseAnalysisActivity;
import com.hevttc.jdr.interiew.view.fragment.BaseFragment;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hegeyang on 2018/5/7.
 */

@SuppressLint("ValidFragment")
public class AnalysisFragment extends BaseFragment {
    private final CheckAnswerBean bean;
    private final int i;
    private final String s;
    @BindView(R.id.tv_analysis_title)
    TextView tvAnalysisTitle;
    @BindView(R.id.tv_analysis_now)
    TextView tvAnalysisNow;
    @BindView(R.id.tv_analysis_all)
    TextView tvAnalysisAll;
    @BindView(R.id.tv_analysis_type)
    TextView tvAnalysisType;
    @BindView(R.id.tv_analysis_content)
    TextView tvAnalysisContent;
    @BindView(R.id.cb_a)
    CheckBox cbA;
    @BindView(R.id.cb_b)
    CheckBox cbB;
    @BindView(R.id.cb_c)
    CheckBox cbC;
    @BindView(R.id.cb_d)
    CheckBox cbD;
    @BindView(R.id.tv_analysis_true)
    TextView tvAnalysisTrue;
    @BindView(R.id.tv_analysis_your)
    TextView tvAnalysisYour;
    @BindView(R.id.tv_analysis_result)
    TextView tvAnalysisResult;
    @BindView(R.id.tv_analysis_body)
    TextView tvAnalysisBody;
    @BindView(R.id.tv_analysis_look)
    TextView tvAnalysisLook;
    Unbinder unbinder;
    private ExerciseAnalysisActivity analysisActivity;
    private BaseBean<List<ExerciseQuestionBean>> baseBean;

    public AnalysisFragment(CheckAnswerBean bean, int i, String s) {
        this.bean = bean;
        this.i = i;
        this.s = s;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_analysis;
    }

    @Override
    public void initDatas() {
        if (bean.getAnswer().length()>1){
            tvAnalysisType.setText("(多选题)");
        }else{
            tvAnalysisType.setText("(单选题)");
        }
        tvAnalysisNow.setText(i+"");
        tvAnalysisAll.setText(analysisActivity.getItemCout()+"");
        analysisActivity.isCollect(bean.isCollect());
        getQuestion();
    }

    private void getQuestion() {
        OkGo.<String>get(Constants.API_EXECRISE_GETSOMEQUESTION)
                .params("id",bean.getQuestionId()+"")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Type type = new TypeToken<BaseBean<List<ExerciseQuestionBean>>>() {
                        }.getType();
                        baseBean = new Gson().fromJson(response.body(), type);
                        if (baseBean.isSuccess()){
                            initQuestion();

                        }else
                            Toast.makeText(mActivity, "失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initQuestion() {
        ExerciseQuestionBean questionBean = baseBean.getData().get(0);
        tvAnalysisContent.setText(questionBean.getQuestion());
        cbA.setText(questionBean.getA());
        cbB.setText(questionBean.getB());
        cbC.setText(questionBean.getC());
        cbD.setText(questionBean.getD());
        tvAnalysisTrue.setText(getAnswerText(bean.getAnswer()));
        tvAnalysisYour.setText(getAnswerText(s));
        if (s.equals(bean.getAnswer()))
            tvAnalysisResult.setText("(正确)");
        else
            tvAnalysisResult.setText("(错误)");
        for (String str : bean.getAnswer().split(",")) {
            if (str.equals("1"))
                cbA.setChecked(true);
            if (str.equals("2"))
                cbB.setChecked(true);
            if (str.equals("3"))
                cbC.setChecked(true);
            if (str.equals("4"))
                cbD.setChecked(true);
        }


    }

    @Override
    public void initViews(View view) {
        analysisActivity = (ExerciseAnalysisActivity) this.mActivity;
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        tvAnalysisLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvAnalysisBody.setText(bean.getAnalysis());
                tvAnalysisBody.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    public void setCollectFlag(boolean flag){
        bean.setCollect(false);
    }
    public String getAnswerText(String answer){
        StringBuilder stringBuilder = new StringBuilder("");
        if (!TextUtils.isEmpty(answer)){
            String[] split = answer.split(",");
            for (String s : split) {
                if (s.equals("1"))
                    stringBuilder.append("A");
                if (s.equals("2"))
                    stringBuilder.append("B");
                if (s.equals("3"))
                    stringBuilder.append("C");
                if (s.equals("4"))
                    stringBuilder.append("D");
            }
        }
        return stringBuilder.toString();
    }
}
