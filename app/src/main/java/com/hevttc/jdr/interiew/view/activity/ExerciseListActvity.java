package com.hevttc.jdr.interiew.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.adapter.ExerciseListAdapter;
import com.hevttc.jdr.interiew.adapter.RvDividerItemDecoration;
import com.hevttc.jdr.interiew.adapter.SecondaryListAdapter;
import com.hevttc.jdr.interiew.bean.BaseBean;
import com.hevttc.jdr.interiew.bean.ExerciesListBean;
import com.hevttc.jdr.interiew.bean.SignStatusBean;
import com.hevttc.jdr.interiew.bean.TestStatusBean;
import com.hevttc.jdr.interiew.util.Constants;
import com.hevttc.jdr.interiew.util.SPUtils;
import com.hevttc.jdr.interiew.util.StatusBarUtil;
import com.hevttc.jdr.interiew.util.TitleBuilder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hegeyang on 2018/4/10.
 */

public class ExerciseListActvity extends BaseActivity {
    @BindView(R.id.title_leftIco)
    ImageView titleLeftIco;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.title_rightIco)
    ImageView titleRightIco;
    @BindView(R.id.title_bar)
    RelativeLayout titleBar;
    @BindView(R.id.top_bar)
    LinearLayout topBar;
    @BindView(R.id.tv_exeli_study_num)
    TextView tvExeliStudyNum;
    @BindView(R.id.tv_exeli_ques_num)
    TextView tvExeliQuesNum;
    @BindView(R.id.tv_exeli_rate)
    TextView tvExeliRate;
    @BindView(R.id.rcy_exeli)
    RecyclerView rcyExeli;
    private ExerciseListAdapter exerciseListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exercise_list;
    }

    @Override
    protected void initViews() {
        new TitleBuilder(this).setTitleText("专项练习").setLeftIco(R.mipmap.row_back).setLeftIcoListening(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        StatusBarUtil.setViewTopPadding(ExerciseListActvity.this, R.id.top_bar);
    }

    @Override
    protected void initDatas() {
        getDataFromNet();
        initTestStatus();
    }

    private void initTestStatus() {
        OkGo.<String>get(Constants.API_TEST_STATUS)
                .params("uid",SPUtils.getSignInfo(mContext).getId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Type type = new TypeToken<BaseBean<TestStatusBean>>() {
                        }.getType();
                        BaseBean<TestStatusBean> baseBean = new Gson().fromJson(response.body(), type);
                        if (baseBean.isSuccess()) {
                            TestStatusBean data = baseBean.getData();
                            tvExeliStudyNum.setText(data.getTestDay()+"");
                            tvExeliQuesNum.setText(data.getTestNum()+"");
                            tvExeliRate.setText(data.getTestRate()+"");
                        }
                    }
                });
    }


    private void getDataFromNet() {
        OkGo.<String>get(Constants.API_EXECRISE_LIST)
                .params("uid",SPUtils.getSignInfo(mContext).getId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Type type = new TypeToken<BaseBean<List<ExerciesListBean>>>() {
                        }.getType();
                        BaseBean<List<ExerciesListBean>> baseBean = new Gson().fromJson(response.body(), type);
                        if (baseBean.isSuccess()) {
                            initRecy(baseBean.getData());
                        }
                    }
                });
    }

    private void initRecy(List<ExerciesListBean> data) {
        rcyExeli.setLayoutManager(new LinearLayoutManager(mContext));
        rcyExeli.setHasFixedSize(true);
        //rcyExeli.addItemDecoration(new RvDividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        exerciseListAdapter = new ExerciseListAdapter(mContext);
        ArrayList<SecondaryListAdapter.DataTree
                <ExerciesListBean, ExerciesListBean.DataListBean>>
                dataTrees = new ArrayList<>();
        for (ExerciesListBean bean : data) {
            SecondaryListAdapter.DataTree dataBean = new SecondaryListAdapter.DataTree(bean, bean.getDataList());
            dataTrees.add(dataBean);
        }
        exerciseListAdapter.setData(dataTrees);
        rcyExeli.setAdapter(exerciseListAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void initListeners() {
    }
}
