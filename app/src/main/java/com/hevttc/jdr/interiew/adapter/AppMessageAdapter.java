package com.hevttc.jdr.interiew.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.bean.MessageBean;

import java.util.List;

/**
 * Created by hegeyang on 2018/5/16.
 */

public class AppMessageAdapter extends BaseQuickAdapter<MessageBean,BaseViewHolder> {
    public AppMessageAdapter(@LayoutRes int layoutResId, @Nullable List<MessageBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean item) {
        helper.setText(R.id.tv_msg_item_title,item.getTitle())
                .setText(R.id.tv_msg_item_create_time,item.getCreateTime());
    }
}
