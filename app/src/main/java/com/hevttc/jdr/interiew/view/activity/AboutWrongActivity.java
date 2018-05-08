package com.hevttc.jdr.interiew.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.adapter.ExerciseListAdapter;
import com.hevttc.jdr.interiew.adapter.SecondaryListAdapter;
import com.hevttc.jdr.interiew.adapter.WrongListAdapter;
import com.hevttc.jdr.interiew.bean.BaseBean;
import com.hevttc.jdr.interiew.bean.ExerciesListBean;
import com.hevttc.jdr.interiew.bean.UserInfoBean;
import com.hevttc.jdr.interiew.bean.WrongListBean;
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
 * Created by hegeyang on 2018/4/28.
 */

public class AboutWrongActivity extends BaseActivity {

    @BindView(R.id.rcy_about_wrong)
    RecyclerView rcyAboutWrong;
    private int type;
    private WrongListAdapter wrongListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_wrong;
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setViewTopPadding(this, R.id.top_bar);
        TitleBuilder titleBuilder = new TitleBuilder(this)
                .setLeftIco(R.mipmap.row_back)
                .setLeftIcoListening(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
        type = getIntent().getExtras().getInt("type");
        if (type == 0)
            titleBuilder.setTitleText("错题浏览");
        else
            titleBuilder.setTitleText("错题练习");
    }

    @Override
    protected void initDatas() {
        getDataFromNet();

    }

    private void getDataFromNet() {
        UserInfoBean signInfo = SPUtils.getSignInfo(mContext);
        OkGo.<String>get(Constants.API_EXEC_USER_WRONGLIST)
                .params("uid", signInfo.getId())
                .params("type", 0)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Type type = new TypeToken<BaseBean<List<WrongListBean>>>() {
                        }.getType();
                        BaseBean<List<WrongListBean>> baseBean = new Gson().fromJson(response.body(), type);
                        if (baseBean.isSuccess()) {
                            initRecy(baseBean.getData());
                        }
                    }
                });
    }

    private void initRecy(List<WrongListBean> data) {
        rcyAboutWrong.setLayoutManager(new LinearLayoutManager(mContext));
        rcyAboutWrong.setHasFixedSize(true);
        //rcyExeli.addItemDecoration(new RvDividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        wrongListAdapter = new WrongListAdapter(mContext,type);
        ArrayList<SecondaryListAdapter.DataTree
                <WrongListBean, WrongListBean.DataListBean>>
                dataTrees = new ArrayList<>();
        for (WrongListBean bean : data) {
            SecondaryListAdapter.DataTree dataBean = new SecondaryListAdapter.DataTree(bean, bean.getDataList());
            dataTrees.add(dataBean);
        }
        wrongListAdapter.setData(dataTrees);
        rcyAboutWrong.setAdapter(wrongListAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
