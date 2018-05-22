package com.hevttc.jdr.interiew.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.bean.BaseBean;
import com.hevttc.jdr.interiew.bean.UserInfoBean;
import com.hevttc.jdr.interiew.bean.UserSettingBean;
import com.hevttc.jdr.interiew.util.Constants;
import com.hevttc.jdr.interiew.util.SPUtils;
import com.hevttc.jdr.interiew.util.StatusBarUtil;
import com.hevttc.jdr.interiew.util.TitleBuilder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.lang.reflect.Type;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hegeyang on 2018/5/9.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_setting_exit)
    TextView tvSettingExit;
    @BindView(R.id.tv_exe_setting_num)
    TextView tvExeSettingNum;
    @BindView(R.id.tv_exe_setting_mode)
    TextView tvExeSettingMode;
    @BindView(R.id.rl_setting_exe_num)
    RelativeLayout rlSettingExeNum;
    @BindView(R.id.rl_setting_exe_model)
    RelativeLayout rlSettingExeModel;
    @BindView(R.id.tv_setting_pwd)
    TextView tvSettingPwd;
    private BaseBean<UserSettingBean> baseBean;
    public HashMap<Integer, String> map = new HashMap<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setViewTopPadding(this, R.id.top_bar);
        new TitleBuilder(this).setLeftIco(R.mipmap.row_back)
                .setTitleText("设置")
                .setLeftIcoListening(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
        map.put(0, "全部题型");
        map.put(1, "只是新题");
        map.put(2, "只是错题");
    }

    @Override
    protected void initDatas() {
        getUserSetting();
    }

    private void getUserSetting() {
        UserInfoBean signInfo = SPUtils.getSignInfo(mContext);
        OkGo.<String>get(Constants.API_GETUSERSETTING)
                .params("uid", signInfo.getId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Type type = new TypeToken<BaseBean<UserSettingBean>>() {
                        }.getType();
                        baseBean = new Gson().fromJson(response.body(), type);
                        if (baseBean.isSuccess()) {
                            UserSettingBean data = baseBean.getData();
                            tvExeSettingNum.setText(data.getExamNum() + "");
                            tvExeSettingMode.setText(map.get(Integer.parseInt(data.getExamType())));
                        }
                    }
                });
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        tvSettingExit.setOnClickListener(this);
        tvSettingPwd.setOnClickListener(this);
        rlSettingExeNum.setOnClickListener(this);
        rlSettingExeModel.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_setting_exit:
                SPUtils.saveString(mContext, Constants.SP_LOGIN, "");
                toActivity(LoginActivity.class);
                finish();
                break;
            case R.id.tv_setting_pwd:
                toActivity(SetPwdActivity.class);
                break;
            case R.id.rl_setting_exe_model:
                break;
            case R.id.rl_setting_exe_num:
                break;
        }
    }
}
