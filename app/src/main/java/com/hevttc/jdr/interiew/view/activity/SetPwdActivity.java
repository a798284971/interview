package com.hevttc.jdr.interiew.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.bean.UserInfoBean;
import com.hevttc.jdr.interiew.util.Constants;
import com.hevttc.jdr.interiew.util.SPUtils;
import com.hevttc.jdr.interiew.util.StatusBarUtil;
import com.hevttc.jdr.interiew.util.TitleBuilder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hegeyang on 2018/5/22.
 */

public class SetPwdActivity extends BaseActivity {
    @BindView(R.id.et_old_pwd)
    EditText etOldPwd;
    @BindView(R.id.et_old_re_pwd)
    EditText etOldRePwd;
    @BindView(R.id.et_code_new_pwd)
    EditText etCodeNewPwd;
    @BindView(R.id.tv_signin_is)
    TextView tvSigninIs;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_pwd14;
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setViewTopPadding(this, R.id.top_bar);
        new TitleBuilder(this).setLeftIco(R.mipmap.row_back)
                .setLeftIcoListening(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .setTitleText("修改密码");
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initListeners() {
        super.initListeners();
        tvSigninIs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old = etOldPwd.getText().toString().trim();
                String reOld = etOldRePwd.getText().toString().trim();
                String newPwd = etCodeNewPwd.getText().toString().trim();
                if (!old.equals(reOld)){
                    Toast.makeText(mContext, "两次密码输入不同，请检查后重试", Toast.LENGTH_SHORT).show();
                    return;
                }
                UserInfoBean signInfo = SPUtils.getSignInfo(mContext);
                OkGo.<String>get(Constants.API_CHANGE_PWD)
                        .params("uid",signInfo.getId())
                        .params("oldpwd",old)
                        .params("newpwd",newPwd)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body());
                                    boolean success = jsonObject.getBoolean("success");
                                    if (success){
                                        Toast.makeText(mContext, "修改成功，请重新登录", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(mContext, LoginActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(mContext, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
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
