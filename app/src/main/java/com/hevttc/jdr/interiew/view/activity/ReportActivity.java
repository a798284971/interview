package com.hevttc.jdr.interiew.view.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.util.StatusBarUtil;
import com.hevttc.jdr.interiew.util.TitleBuilder;

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
                //TODO  立即反馈按钮的单击事件

            }
        });
        //TODO 保存EditText中的内容,提交到服务器数据库中
    }
}
