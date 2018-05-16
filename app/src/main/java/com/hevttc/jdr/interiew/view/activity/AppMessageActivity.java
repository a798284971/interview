package com.hevttc.jdr.interiew.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.adapter.AppMessageAdapter;
import com.hevttc.jdr.interiew.bean.MessageBean;
import com.hevttc.jdr.interiew.util.StatusBarUtil;
import com.hevttc.jdr.interiew.util.TitleBuilder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hegeyang on 2018/5/16.
 */

public class AppMessageActivity extends BaseActivity {
    @BindView(R.id.rcy_app_message)
    RecyclerView rcyAppMessage;
    private ArrayList<MessageBean> data;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_app_message;
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setViewTopPadding(this, R.id.top_bar);
        data = getIntent().getExtras().getParcelableArrayList("data");
        //Log.e("hgy", data.get(0).getTitle());
    }

    @Override
    protected void initDatas() {
        new TitleBuilder(this).setTitleText("消息详情").setLeftIco(R.mipmap.row_back).setLeftIcoListening(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rcyAppMessage.setLayoutManager(new LinearLayoutManager(mContext));
        AppMessageAdapter appMessageAdapter = new AppMessageAdapter(R.layout.item_app_message, data);
        rcyAppMessage.setAdapter(appMessageAdapter);
        appMessageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Uri uri = Uri.parse(data.get(position).getContent());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
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
