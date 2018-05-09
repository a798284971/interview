package com.hevttc.jdr.interiew.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.util.Constants;
import com.hevttc.jdr.interiew.util.SPUtils;
import com.hevttc.jdr.interiew.util.StatusBarUtil;
import com.hevttc.jdr.interiew.util.TitleBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hegeyang on 2018/5/9.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_setting_exit)
    TextView tvSettingExit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setViewTopPadding(this,R.id.top_bar);
        new TitleBuilder(this).setLeftIco(R.mipmap.row_back)
                .setTitleText("设置")
                .setLeftIcoListening(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initListeners() {
        super.initListeners();
        tvSettingExit.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_setting_exit:
                SPUtils.saveString(mContext, Constants.SP_LOGIN,"");
                toActivity(LoginActivity.class);
                finish();
                break;
        }
    }
}
