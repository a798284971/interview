package com.hevttc.jdr.interiew.adapter;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hevttc.jdr.interiew.MyApplication;
import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.bean.BaseBean;
import com.hevttc.jdr.interiew.bean.SignTalkBean;
import com.hevttc.jdr.interiew.bean.UserInfoBean;
import com.hevttc.jdr.interiew.util.Constants;
import com.hevttc.jdr.interiew.util.SPUtils;
import com.hevttc.jdr.interiew.util.TimeUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.lang.reflect.Type;
import java.sql.Time;
import java.util.List;
import java.util.Random;

/**
 * Created by hegeyang on 2018/5/8.
 */

public class SignListAdapter extends BaseQuickAdapter<SignTalkBean,BaseViewHolder> {
    private CompoundButton.OnCheckedChangeListener listener;

    public SignListAdapter(@LayoutRes int layoutResId, @Nullable List<SignTalkBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final SignTalkBean item) {
        TextView tv_sign_item_nick = helper.getView(R.id.tv_sign_item_nick);
        tv_sign_item_nick.setTextColor(getRamColor());
        helper.setText(R.id.tv_sign_item_nick,item.getNickName())
                .setText(R.id.tv_sign_item_time,item.getCreateTime())
                .setText(R.id.tv_sign_item_content,item.getTalkText())
                .setChecked(R.id.cb_item_fight,item.getStatus()==1);
        helper.addOnClickListener(R.id.cb_item_talk)
                .addOnClickListener(R.id.cb_item_share);

            helper.setOnCheckedChangeListener(R.id.cb_item_fight, new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (TimeUtils.isFastClick()){
                        if (isChecked)
                            commitFight(0,helper,item);
                        else
                            commitFight(1,helper,item);
                    }
                }
            });
    }
    public int getRamColor(){
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        int color = Color.rgb(r,g,b);
        return color;
    }
    private void commitFight(final int temp, final BaseViewHolder helper, SignTalkBean item) {
        UserInfoBean signInfo = SPUtils.getSignInfo(MyApplication.getMyApplicaiton());
        OkGo.<String>get(Constants.API_SIGN_FIGHT)
                .params("uid",signInfo.getId()+"")
                .params("talkId",item.getTalkId()+"")
                .params("type",temp+"")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        CheckBox cb = helper.getView(R.id.cb_item_fight);
                        if (temp==0){
                            Type type = new TypeToken<BaseBean<String>>() {
                            }.getType();
                            BaseBean<String> baseBean = new Gson().fromJson(response.body(), type);

                            cb.setText(baseBean.getData());
                        }else{
                            cb.setText("");
                        }

                    }
                });
    }
}
