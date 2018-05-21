package com.hevttc.jdr.interiew.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.adapter.MyReportAdapter;
import com.hevttc.jdr.interiew.bean.BaseBean;
import com.hevttc.jdr.interiew.bean.MyReportBean;
import com.hevttc.jdr.interiew.bean.UserInfoBean;
import com.hevttc.jdr.interiew.util.Constants;
import com.hevttc.jdr.interiew.util.SPUtils;
import com.hevttc.jdr.interiew.util.StatusBarUtil;
import com.hevttc.jdr.interiew.util.TitleBuilder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hegeyang on 2018/5/21.
 */

public class ReportListActivity extends BaseActivity {
    @BindView(R.id.rcy_my_report)
    RecyclerView rcyMyReport;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_report_list;
    }

    @Override
    protected void initViews() {
        new TitleBuilder(this)
                .setLeftIco(R.mipmap.row_back)
                .setTitleText("我的小报告")
                .setLeftIcoListening(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
        StatusBarUtil.setViewTopPadding(this, R.id.top_bar);
    }

    @Override
    protected void initDatas() {
        rcyMyReport.setLayoutManager(new LinearLayoutManager(mContext));
        getData();
    }

    private void getData() {
        UserInfoBean signInfo = SPUtils.getSignInfo(mContext);
        OkGo.<String>get(Constants.API_GETREPORT)
                .params("uid",signInfo.getId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Type type = new TypeToken<BaseBean<List<MyReportBean>>>() {
                        }.getType();
                        //Log.e("hgy", response.body().toString() );
                        BaseBean<List<MyReportBean>> baseBean = new Gson().fromJson(response.body(), type);
                        if (baseBean.isSuccess()){
                            rcyMyReport.setAdapter(new MyReportAdapter(R.layout.item_app_message,baseBean.getData()));
                        }else{
                            Toast.makeText(mContext, "获取信息失败请稍后重试", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Toast.makeText(mContext, "获取信息失败请稍后重试", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
