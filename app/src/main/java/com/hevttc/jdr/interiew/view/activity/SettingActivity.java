package com.hevttc.jdr.interiew.view.activity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import org.json.JSONException;
import org.json.JSONObject;

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
    private UserSettingBean data;

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
        map.put(1, "只出新题");
        map.put(2, "只出错题");
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
                            data = baseBean.getData();
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
                if (data!=null)
                    showModelDialog();
                else
                    Toast.makeText(mContext, "数据还没加载完，请稍后点击", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_setting_exe_num:
                if (data!=null)
                    showNumDialog();
                else
                    Toast.makeText(mContext, "数据还没加载完，请稍后点击", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void showNumDialog() {
        View numView = getLayoutInflater().inflate(
                R.layout.dialog_settting_num, null);
        final EditText et_set_num = numView.findViewById(R.id.et_set_num);
        TextView tv_setting_num_sure = numView.findViewById(R.id.tv_setting_num_sure);
        TextView tv_setting_num_cancel = numView.findViewById(R.id.tv_setting_num_cancel);
        et_set_num.setText(data.getExamNum()+"");
        et_set_num.setSelection(et_set_num.getText().toString().length());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.setView(numView).create();
        alertDialog.show();
        tv_setting_num_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = et_set_num.getText().toString().trim();
                if (Integer.parseInt(num)==data.getExamNum())
                    return;
                else
                    setNum(num);
                alertDialog.dismiss();
            }
        });
        tv_setting_num_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    private void showModelDialog() {
        View modelView = getLayoutInflater().inflate(
                R.layout.dialog_setting_model, null);
        RelativeLayout rl_model_1 = modelView.findViewById(R.id.rl_model_1);
        RelativeLayout rl_model_2 = modelView.findViewById(R.id.rl_model_2);
        RelativeLayout rl_model_3 = modelView.findViewById(R.id.rl_model_3);
        final ImageView iv_model_1 = modelView.findViewById(R.id.iv_model_1);
        final ImageView iv_model_2 = modelView.findViewById(R.id.iv_model_2);
        final ImageView iv_model_3 = modelView.findViewById(R.id.iv_model_3);
        int nowModel = Integer.parseInt(data.getExamType());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.setView(modelView).create();
        alertDialog.show();
        switch (nowModel) {
            case 0:
                iv_model_1.setVisibility(View.VISIBLE);
                iv_model_2.setVisibility(View.INVISIBLE);
                iv_model_3.setVisibility(View.INVISIBLE);
                break;
            case 1:
                iv_model_1.setVisibility(View.INVISIBLE);
                iv_model_2.setVisibility(View.VISIBLE);
                iv_model_3.setVisibility(View.INVISIBLE);
                break;
            case 2:
                iv_model_1.setVisibility(View.INVISIBLE);
                iv_model_2.setVisibility(View.INVISIBLE);
                iv_model_3.setVisibility(View.VISIBLE);
        }
        rl_model_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_model_1.setVisibility(View.VISIBLE);
                iv_model_2.setVisibility(View.INVISIBLE);
                iv_model_3.setVisibility(View.INVISIBLE);
                setModel(0);
                alertDialog.dismiss();
            }
        });
        rl_model_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_model_1.setVisibility(View.INVISIBLE);
                iv_model_2.setVisibility(View.VISIBLE);
                iv_model_3.setVisibility(View.INVISIBLE);
                setModel(1);
                alertDialog.dismiss();
            }
        });
        rl_model_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_model_1.setVisibility(View.INVISIBLE);
                iv_model_2.setVisibility(View.INVISIBLE);
                iv_model_3.setVisibility(View.VISIBLE);
                setModel(2);
                alertDialog.dismiss();
            }
        });

    }

    private void setNum(final String num) {
        UserInfoBean signInfo = SPUtils.getSignInfo(mContext);
        OkGo.<String>get(Constants.API_SENDUSERSETTING)
                .params("uid",signInfo.getId())
                .params("examNum",num)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            boolean success = jsonObject.getBoolean("success");
                            if (success){
                                tvExeSettingNum.setText(num);
                                data.setExamNum(Integer.parseInt(num));
                            }else{
                                Toast.makeText(mContext, "修改失败请稍后重试", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void setModel(final int i) {
        UserInfoBean signInfo = SPUtils.getSignInfo(mContext);
        OkGo.<String>get(Constants.API_SENDUSERSETTING)
                .params("uid",signInfo.getId())
                .params("examType",i)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            boolean success = jsonObject.getBoolean("success");
                            if (success){
                                tvExeSettingMode.setText(map.get(i));
                                data.setExamType(i+"");
                            }else{
                                Toast.makeText(mContext, "修改失败请稍后重试", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

}
