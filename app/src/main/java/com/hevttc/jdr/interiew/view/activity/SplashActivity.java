package com.hevttc.jdr.interiew.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.bean.BaseBean;
import com.hevttc.jdr.interiew.bean.UserInfoBean;
import com.hevttc.jdr.interiew.util.Constants;
import com.hevttc.jdr.interiew.util.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.animation;

/**
 * Created by hegeyang on 2017/12/22.
 */

public class SplashActivity extends BaseActivity implements Animation.AnimationListener {
    @BindView(R.id.rl_splash)
    RelativeLayout rlSplash;

    private Handler handler = new Handler();
    private Animation animation;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViews() {
        animation = createAnimation();
        rlSplash.startAnimation(animation);

    }

    private Animation createAnimation() {
        AnimationSet set = new AnimationSet(false);
        //缩放
        ScaleAnimation scale = new ScaleAnimation(0.9f, 1, 0.9f, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(2000);
        //透明度
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        alpha.setDuration(2000);
        set.addAnimation(scale);
        set.addAnimation(alpha);
        return set;
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void initListeners() {
        //监听动画
        animation.setAnimationListener(this);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        //延时2s进入向导界面GuideActivity  该方法在主线程   发送一个延时的消息
        handler.postDelayed(new MyTask(), 1500);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
    private class MyTask implements Runnable {

        @Override
        public void run() {

            UserInfoBean signInfo = SPUtils.getSignInfo(SplashActivity.this);
            boolean aBoolean = SPUtils.getBoolean(SplashActivity.this, Constants.REME_FIRST, false);
            if (!aBoolean) {
                toActivity(GuideActivity.class);
                finish();
            }else {
                String mPhone = SPUtils.getString(mContext, Constants.REME_NAME, "");
                String mPwd = SPUtils.getString(mContext, Constants.REME_PASS, "");
                if (!TextUtils.isEmpty(mPhone) && !TextUtils.isEmpty(mPwd))
                    OkGo.<String>get(Constants.API_LOGININ)
                            .params("username",mPhone)
                            .params("password",mPwd)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Type type = new TypeToken<BaseBean<UserInfoBean>>() {
                                    }.getType();
                                    //Log.e("hgy", response.body().toString() );
                                    BaseBean<UserInfoBean> baseBean = new Gson().fromJson(response.body(), type);
                                    if(baseBean.isSuccess()){
                                        SPUtils.saveString(SplashActivity.this,Constants.SP_LOGIN,response.body());
                                        toActivity(MainActivity.class);
                                    }else{
                                        toActivity(LoginActivity.class);
                                    }
                                    finish();
                                }

                                @Override
                                public void onError(Response<String> response) {
                                    super.onError(response);
                                    toActivity(LoginActivity.class);
                                    finish();
                                }
                            });
                else {
                    toActivity(LoginActivity.class);
                    finish();
                }

            }

        }
    }
}
