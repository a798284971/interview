package com.hevttc.jdr.interiew.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.bean.UserCollectBean;

import java.util.List;

/**
 * Created by hegeyang on 2018/4/28.
 */

public class UserCollectAdapter extends BaseQuickAdapter<UserCollectBean,BaseViewHolder> {
    public UserCollectAdapter(@LayoutRes int layoutResId, @Nullable List<UserCollectBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserCollectBean item) {
        helper.setText(R.id.tv_item_collect_title,item.getTitle())
            .setText(R.id.tv_item_collect_time,item.getCreateTime());

    }
}
