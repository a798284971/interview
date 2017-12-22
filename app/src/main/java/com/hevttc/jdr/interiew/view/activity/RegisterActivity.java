package com.hevttc.jdr.interiew.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.hevttc.jdr.interiew.bean.LunboBean;
import com.hevttc.jdr.interiew.util.Constants;
import com.hevttc.jdr.interiew.util.REGutil;
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
 * Created by hegeyang on 2017/12/22.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
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
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.cb_register_pwd_visible)
    CheckBox cbRegisterPwdVisible;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.tv_signin)
    TextView tvSignin;
    @BindView(R.id.tv_protocol)
    TextView tvProtocol;
    private String mPhone;
    private String mCode;
    private String mPwd;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setViewTopPadding(this, R.id.top_bar);
        new TitleBuilder(this).setTitleText("注册").setLeftIco(R.mipmap.row_back).setLeftIcoListening(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initListeners() {
        tvGetCode.setOnClickListener(this);
        tvSignin.setOnClickListener(this);
        cbRegisterPwdVisible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    //如果选中，显示密码
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    //否则隐藏密码
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                etPassword.setSelection(etPassword.getText().toString().trim().length());
            }
        });
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
            case R.id.tv_get_code:
                getVerifyCode();
                break;
            case R.id.tv_signin:
                /*正常登录状态*/
                mPhone = etPhoneNumber.getText().toString().trim();
                mCode = etCode.getText().toString().trim();
                mPwd = etPassword.getText().toString().trim();
                if (TextUtils.isEmpty(mPhone)) {
                    Toast.makeText(mContext, "手机号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!REGutil.checkCellphone(mPhone)){
                    Toast.makeText(mContext, "手机号格式好像输错了，检查一下吧", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(mCode)){
                    Toast.makeText(mContext, "验证码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(mPwd)){
                    Toast.makeText(mContext, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                doRegister();
                break;
        }
    }

    private void doRegister() {
        OkGo.<String>get(Constants.API_REGISTER)
                .params("username",mPhone)
                .params("password",mPwd)
                .params("code",mCode)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Type type = new TypeToken<BaseBean<String>>() {
                        }.getType();
                        BaseBean<String> baseBean = new Gson().fromJson(response.body(), type);
                        if(baseBean.isSuccess()){
                            Toast.makeText(mContext, "注册成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Message obtain = Message.obtain();
                            obtain.what=MSG_SEND_CODE_ERROR;
                            obtain.obj = baseBean.getMsg();
                            handler.sendMessage(obtain);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Message obtain = Message.obtain();
                        obtain.what=MSG_SEND_CODE_ERROR;
                        obtain.obj = "网络连接失败";
                        handler.sendMessage(obtain);
                    }
                });
    }

    private void getVerifyCode() {
        mPhone = etPhoneNumber.getText().toString().trim();
        if (TextUtils.isEmpty(mPhone)) {
            Toast.makeText(mContext, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!REGutil.checkCellphone(mPhone)){
            Toast.makeText(mContext, "手机号格式好像输错了，检查一下吧", Toast.LENGTH_SHORT).show();
            return;
        }
        OkGo.<String>get(Constants.API_SENDMESSAGE)
                .params("number",mPhone)
                .execute(new StringCallback() {

            @Override
            public void onSuccess(Response<String> response) {
                Type type = new TypeToken<BaseBean<String>>() {
                }.getType();
                BaseBean<String> baseBean= new Gson().fromJson(response.body(), type);
                if (baseBean.isSuccess()) {
                    handler.sendEmptyMessage(MSG_SEND_CODE_SUCCESS);
                } else {
                    Message obtain = Message.obtain();
                    obtain.what=MSG_SEND_CODE_ERROR;
                    obtain.obj = baseBean.getMsg();
                    handler.sendMessage(obtain);
                }
            }

            @Override
            public void onError(Response<String> response) {
                Message obtain = Message.obtain();
                obtain.what=MSG_SEND_CODE_ERROR;
                obtain.obj = "网络连接失败";
                handler.sendMessage(obtain);
            }
        });
    }
    private TimerHandler handler = new TimerHandler();

    private static final int MSG_SEND_CODE_SUCCESS = 0;
    private static final int MSG_SEND_CODE_ERROR = 1;
    private static final int MSG_TIMER = 2;

    private int time = 60;

    private class TimerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            /*验证码倒计时*/
            switch (msg.what) {
                case MSG_SEND_CODE_SUCCESS:
                    Toast.makeText(mContext, "验证码发送成功", Toast.LENGTH_SHORT).show();
                    handler.sendEmptyMessage(MSG_TIMER);
                    break;

                case MSG_SEND_CODE_ERROR:
                    Toast.makeText(mContext, (String)msg.obj, Toast.LENGTH_SHORT).show();
                    break;

                case MSG_TIMER:
                    startTimer();
                    break;
            }
        }
    }
    private void startTimer() {
        if (time > 0) {
            tvGetCode.setText(String.format("倒计时%s秒", time));
            time -= 1;
            chageSendStatus(false);
            handler.sendEmptyMessageDelayed(MSG_TIMER, 1000);
        } else {
            tvGetCode.setText("获取验证码");
            time = 60;
            chageSendStatus(true);
        }
    }
    /**
     * 设置是否可发送短信
     */
    private void chageSendStatus(boolean canSend) {
        if (canSend) {
            tvGetCode.setEnabled(true);
            tvGetCode.setClickable(true);
        } else {
            tvGetCode.setEnabled(false);
            tvGetCode.setClickable(false);
        }
    }
}
