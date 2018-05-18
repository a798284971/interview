package com.hevttc.jdr.interiew.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hevttc.jdr.interiew.MyApplication;
import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.bean.CommentListBean;
import com.hevttc.jdr.interiew.util.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2018-5-17.
 */

public class CommentListAdapter extends BaseQuickAdapter<CommentListBean.DataBean, BaseViewHolder> {

    public CommentListAdapter(int layoutResId, List<CommentListBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentListBean.DataBean item) {
        helper.setText(R.id.comment_item_userName, item.getNickName())
                .setText(R.id.comment_item_time, item.getTime())
                .setText(R.id.comment_item_content, item.getText());
        String s = String.valueOf(item.getHeadImg());
        if (!TextUtils.isEmpty(s))
            Picasso.with(MyApplication.getMyApplicaiton())
                    .load(s)
                    .transform(new CircleTransform())
                    .fit()
                    .error(R.mipmap.center_icon)
                    .placeholder(R.mipmap.center_icon)
                    .into((ImageView) helper.getView(R.id.comment_item_logo));
        else
            helper.setImageResource(R.id.comment_item_logo,R.mipmap.center_icon);


    }


}
