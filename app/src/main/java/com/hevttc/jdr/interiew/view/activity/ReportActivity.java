package com.hevttc.jdr.interiew.view.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
 * Created by Administrator on 2018-5-18.
 */

public class ReportActivity extends BaseActivity {
    @BindView(R.id.bt_report)
    Button bt_report;
    @BindView(R.id.et_report)
    EditText et_report;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_report;
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        StatusBarUtil.setViewTopPadding(this, R.id.top_bar);


    }

    @Override
    protected void initDatas() {
        TitleBuilder titleBuilder = new TitleBuilder(this)
                .setLeftIco(R.mipmap.row_back)
                .setLeftIcoListening(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
        titleBuilder.setTitleText("打个小报告");
        bt_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendReport();
            }
        });
    }

    private void sendReport() {
        UserInfoBean signInfo = SPUtils.getSignInfo(mContext);
        String trim = et_report.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            Toast.makeText(mContext, "打个小报告吧", Toast.LENGTH_SHORT).show();
            return;
        }
        OkGo.<String>get(Constants.API_REPORT)
                .params("uid",signInfo.getId())
                .params("content",trim)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            Toast.makeText(mContext, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


    }
}
