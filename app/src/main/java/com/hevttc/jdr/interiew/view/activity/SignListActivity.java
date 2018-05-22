package com.hevttc.jdr.interiew.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.adapter.MySignListAdapter;
import com.hevttc.jdr.interiew.adapter.SecondaryListAdapter;
import com.hevttc.jdr.interiew.bean.BaseBean;
import com.hevttc.jdr.interiew.bean.ExerciesListBean;
import com.hevttc.jdr.interiew.bean.MySignListBean;
import com.hevttc.jdr.interiew.bean.UserInfoBean;
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
 * Created by hegeyang on 2018/5/22.
 */

public class SignListActivity extends BaseActivity {
    @BindView(R.id.rcy_sign_list)
    RecyclerView rcySignList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign_list;
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setViewTopPadding(this, R.id.top_bar);
        new TitleBuilder(this).setLeftIco(R.mipmap.row_back)
                .setTitleText("我的打卡")
                .setLeftIcoListening(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
    }

    @Override
    protected void initDatas() {
        rcySignList.setLayoutManager(new LinearLayoutManager(mContext));
        getSignListData();
    }

    private void getSignListData() {
        UserInfoBean signInfo = SPUtils.getSignInfo(mContext);
        OkGo.<String>get(Constants.API_GET_SIGN_LIST)
                .params("uid",signInfo.getId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Type type = new TypeToken<BaseBean<List<MySignListBean>>>() {
                        }.getType();
                        BaseBean<List<MySignListBean>> baseBean = new Gson().fromJson(response.body(), type);
                        if (baseBean.isSuccess()){
                            initRcy(baseBean.getData());
                        }
                    }
                });
    }

    private void initRcy(List<MySignListBean> data) {
        rcySignList.setHasFixedSize(true);
        MySignListAdapter mySignListAdapter = new MySignListAdapter(mContext);
        ArrayList<SecondaryListAdapter.DataTree
                <MySignListBean, MySignListBean.TalkListBean>>
                dataTrees = new ArrayList<>();
        for (MySignListBean bean : data) {
            SecondaryListAdapter.DataTree dataBean = new SecondaryListAdapter.DataTree(bean, bean.getTalkList());
            dataTrees.add(dataBean);
        }
        mySignListAdapter.setData(dataTrees);
        rcySignList.setAdapter(mySignListAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
