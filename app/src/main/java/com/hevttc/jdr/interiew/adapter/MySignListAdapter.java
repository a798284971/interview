package com.hevttc.jdr.interiew.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hevttc.jdr.interiew.MyApplication;
import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.bean.ExerciesListBean;
import com.hevttc.jdr.interiew.bean.MySignListBean;
import com.hevttc.jdr.interiew.util.CircleTransform;
import com.hevttc.jdr.interiew.util.DensityUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by hegeyang on 2018/5/22.
 */

public class MySignListAdapter extends SecondaryListAdapter<MySignListAdapter.GroupItemViewHolder, MySignListAdapter.SubItemViewHolder>{
    private Context context;
    private List<DataTree<MySignListBean, MySignListBean.TalkListBean>> dts;


    public MySignListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<SecondaryListAdapter.DataTree
            <MySignListBean, MySignListBean.TalkListBean>> datas) {

        dts = datas;
        notifyNewData(dts);
    }

    @Override
    public RecyclerView.ViewHolder groupItemViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mysign_recy_item, parent, false);

        return new MySignListAdapter.GroupItemViewHolder(v);
    }

    @Override
    public RecyclerView.ViewHolder subItemViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment_list, parent, false);

        return new MySignListAdapter.SubItemViewHolder(v);
    }

    @Override
    public void onGroupItemBindViewHolder(RecyclerView.ViewHolder holder, int groupItemIndex) {
        MySignListBean groupItem = dts.get(groupItemIndex).getGroupItem();
        MySignListAdapter.GroupItemViewHolder holder1 = (MySignListAdapter.GroupItemViewHolder) holder;
        holder1.tv_group_item_content.setText(groupItem.getContent());
        holder1.tv_group_item_time.setText(groupItem.getCreateTime());
    }

    @Override
    public void onSubItemBindViewHolder(RecyclerView.ViewHolder holder, int groupItemIndex, int subItemIndex) {
        MySignListBean.TalkListBean dataListBean = dts.get(groupItemIndex).getSubItems().get(subItemIndex);
        MySignListAdapter.SubItemViewHolder holder1 = (MySignListAdapter.SubItemViewHolder) holder;
        holder1.comment_item_userName.setText(dataListBean.getNickName());
        holder1.comment_item_time.setText(dataListBean.getTime());
        holder1.comment_item_content.setText(dataListBean.getText());
        String s = String.valueOf(dataListBean.getHeadImg());
        Picasso.with(context)
                .load(s)
                .transform(new CircleTransform())
                .fit()
                .error(R.mipmap.center_icon)
                .placeholder(R.mipmap.center_icon)
                .into(holder1.comment_item_logo);
    }

    @Override
    public void onGroupItemClick(Boolean isExpand, GroupItemViewHolder holder, int groupItemIndex) {
        holder.cb_item.setChecked(!holder.cb_item.isChecked());
    }

    @Override
    public void onSubItemClick(SubItemViewHolder holder, int groupItemIndex, int subItemIndex) {

    }

    public static class GroupItemViewHolder extends RecyclerView.ViewHolder {


        TextView tv_group_item_content;
        TextView tv_group_item_time;
        CheckBox cb_item;
        public GroupItemViewHolder(View itemView) {
            super(itemView);
            tv_group_item_content = itemView.findViewById(R.id.tv_group_item_content);
            tv_group_item_time = itemView.findViewById(R.id.tv_group_item_time);
            cb_item = itemView.findViewById(R.id.cb_item);
            Drawable buttonDrawable = cb_item.getCompoundDrawables()[0];
            buttonDrawable.setBounds(0,0, DensityUtil.dp2px(itemView.getContext(),15),DensityUtil.dp2px(itemView.getContext(),15));
            cb_item.setCompoundDrawables(buttonDrawable,null,null,null);
        }
    }

    public static class SubItemViewHolder extends RecyclerView.ViewHolder {

        ImageView comment_item_logo;
        TextView comment_item_userName;
        TextView comment_item_time;
        TextView comment_item_content;

        public SubItemViewHolder(View itemView) {
            super(itemView);
            comment_item_logo = itemView.findViewById(R.id.comment_item_logo);
            comment_item_userName = itemView.findViewById(R.id.comment_item_userName);
            comment_item_time = itemView.findViewById(R.id.comment_item_time);
            comment_item_content = itemView.findViewById(R.id.comment_item_content);
        }
    }
}
