package com.hevttc.jdr.interiew.view.activity;

import android.view.View;

import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.bean.UserInfoBean;
import com.hevttc.jdr.interiew.util.Constants;
import com.hevttc.jdr.interiew.util.SPUtils;
import com.hevttc.jdr.interiew.util.StatusBarUtil;
import com.hevttc.jdr.interiew.util.TitleBuilder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

/**
 * Created by hegeyang on 2018/4/28.
 */

public class AboutWrongActivity extends BaseActivity {

    private int type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_wrong;
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setViewTopPadding(this,R.id.top_bar);
        TitleBuilder titleBuilder = new TitleBuilder(this)
                .setLeftIco(R.mipmap.row_back)
                .setLeftIcoListening(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
        type = getIntent().getExtras().getInt("type");
        if (type ==0)
            titleBuilder.setTitleText("错题浏览");
        else
            titleBuilder.setTitleText("错题练习");
    }

    @Override
    protected void initDatas() {
       getDataFromNet();

    }

    private void getDataFromNet() {
        UserInfoBean signInfo = SPUtils.getSignInfo(mContext);
        OkGo.<String>get(Constants.API_EXEC_USER_WRONGLIST)
                .params("uid",signInfo.getId())
                .params("type",0)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                    }
                });
    }
}
