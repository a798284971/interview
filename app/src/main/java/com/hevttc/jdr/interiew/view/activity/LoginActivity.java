package com.hevttc.jdr.interiew.view.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.bean.BaseBean;
import com.hevttc.jdr.interiew.bean.UserInfoBean;
import com.hevttc.jdr.interiew.util.Constants;
import com.hevttc.jdr.interiew.util.SPUtils;
import com.hevttc.jdr.interiew.util.StatusBarUtil;
import com.hevttc.jdr.interiew.util.TitleBuilder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hegeyang on 2017/12/22.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
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
    @BindView(R.id.iv_login_icon)
    ImageView ivLoginIcon;
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.et_code_pwd)
    EditText etCode;
    @BindView(R.id.tv_signin_is)
    TextView tvSignin;
    @BindView(R.id.ll_login_input)
    LinearLayout llLoginInput;
    @BindView(R.id.tv_to_register)
    TextView tvToRegister;
    @BindView(R.id.iv_wx_signin)
    ImageView ivWxSignin;
    @BindView(R.id.iv_qq_signin)
    ImageView ivQqSignin;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setViewTopPadding(this, R.id.top_bar);
    }

    @Override
    protected void initDatas() {
        new TitleBuilder(this).setTitleText("登录").setLeftIco(R.mipmap.row_back).setLeftIcoListening(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initListeners() {
        tvToRegister.setOnClickListener(this);
        tvSignin.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_to_register:
                toActivity(RegisterActivity.class);
                break;
            case R.id.tv_signin_is:
                isSignIn();
                break;
        }
    }

    private void isSignIn() {
        final String mPhone = etPhoneNumber.getText().toString().trim();
        String mPwd = etCode.getText().toString().trim();
        if (TextUtils.isEmpty(mPhone) || TextUtils.isEmpty(mPwd)){
            Toast.makeText(mContext, "账号和密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        OkGo.<String>get(Constants.API_LOGININ)
                .params("username",mPhone)
                .params("password",mPwd)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Type type = new TypeToken<BaseBean<UserInfoBean>>() {
                        }.getType();
                        BaseBean<UserInfoBean> baseBean = new Gson().fromJson(response.body(), type);
                        if(baseBean.isSuccess()){
                            SPUtils.saveString(mContext,Constants.SP_LOGIN,response.body());
                            Toast.makeText(mContext, "登录成功", Toast.LENGTH_SHORT).show();
                            toActivity(MainActivity.class);
                            finish();
                        }else{
                            Toast.makeText(mContext, baseBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
