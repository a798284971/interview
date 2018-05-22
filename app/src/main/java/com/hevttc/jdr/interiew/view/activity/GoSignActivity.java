package com.hevttc.jdr.interiew.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.bean.BaseBean;
import com.hevttc.jdr.interiew.bean.SignStatusBean;
import com.hevttc.jdr.interiew.bean.UserInfoBean;
import com.hevttc.jdr.interiew.util.Constants;
import com.hevttc.jdr.interiew.util.DateUtil;
import com.hevttc.jdr.interiew.util.SPUtils;
import com.hevttc.jdr.interiew.util.StatusBarUtil;
import com.hevttc.jdr.interiew.util.TitleBuilder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hegeyang on 2018/5/22.
 */

public class GoSignActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_go_sign_list)
    TextView tvGoSignList;
    @BindView(R.id.tv_go_sign_date)
    TextView tvGoSignDate;
    @BindView(R.id.tv_study_questionNum)
    TextView tvStudyQuestionNum;
    @BindView(R.id.tv_study_studyNum)
    TextView tvStudyStudyNum;
    @BindView(R.id.et_go_sign_text)
    EditText etGoSignText;
    @BindView(R.id.tv_go_sign_send)
    TextView tvGoSignSend;
    @BindView(R.id.tv_go_sign_relook)
    TextView tvGoSignRelook;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_go_sign;
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setViewTopPadding(this, R.id.top_bar);
        new TitleBuilder(this).setLeftIco(R.mipmap.row_back)
                .setTitleText("打卡")
                .setLeftIcoListening(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
    }

    @Override
    protected void initDatas() {
        initDate();
        initStatus();
    }

    private void initStatus() {
        OkGo.<String>get(Constants.API_SIGN_STATUS)
                .params("uid", SPUtils.getSignInfo(mContext).getId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Type type = new TypeToken<BaseBean<SignStatusBean>>() {
                        }.getType();
                        BaseBean<SignStatusBean> baseBean = new Gson().fromJson(response.body(), type);
                        if (baseBean.isSuccess()) {
                            SignStatusBean data = baseBean.getData();
                            tvStudyQuestionNum.setText(data.getQuestionNum()+"");
                            tvStudyStudyNum.setText(data.getStudyNum()+"");
                        }
                    }
                });
    }

    private void initDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String[] wee = {"", "天", "一", "二", "三", "四", "五", "六"};

        tvGoSignDate.setText(dateFormat.format(new Date()) + " " + "星期" + wee[Calendar.getInstance().get(Calendar.DAY_OF_WEEK)]);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        tvGoSignList.setOnClickListener(this);
        tvGoSignRelook.setOnClickListener(this);
        tvGoSignSend.setOnClickListener(this);
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
            case R.id.tv_go_sign_relook:
                finish();
                break;
            case R.id.tv_go_sign_send:
                sendSign();
                break;
            case R.id.tv_go_sign_list:
                toActivity(SignListActivity.class);
                break;
        }
    }

    private void sendSign() {
        UserInfoBean signInfo = SPUtils.getSignInfo(mContext);
        OkGo.<String>get(Constants.API_SEND_SIGN)
                .params("uid",signInfo.getId())
                .params("content",etGoSignText.getText().toString().trim())
                .params("timeStamp", DateUtil.getTimesTamp(new Date()))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            boolean success = jsonObject.getBoolean("success");
                            if (success){
                                Toast.makeText(mContext, "打卡成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(mContext, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
