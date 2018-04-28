package com.hevttc.jdr.interiew.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.bean.UnivisityBean;

import java.util.List;

/**
 * Created by hegeyang on 2018/4/28.
 */

public class UnivisityAdapter extends BaseQuickAdapter<UnivisityBean,BaseViewHolder> {
    public UnivisityAdapter(@LayoutRes int layoutResId, @Nullable List<UnivisityBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UnivisityBean item) {
        helper.setText(R.id.tv_item_univisity,item.getName());
    }
}
