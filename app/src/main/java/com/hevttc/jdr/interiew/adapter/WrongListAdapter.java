package com.hevttc.jdr.interiew.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.bean.ExerciesListBean;
import com.hevttc.jdr.interiew.bean.UserCollectBean;
import com.hevttc.jdr.interiew.bean.WrongListBean;
import com.hevttc.jdr.interiew.util.DensityUtil;
import com.hevttc.jdr.interiew.view.activity.ExerciseTestActivity;

import java.util.List;


/**
 * Created by hegeyang on 2018/5/8.
 */

public class WrongListAdapter extends SecondaryListAdapter<WrongListAdapter.GroupItemViewHolder, WrongListAdapter.SubItemViewHolder> {
    private final int type;
    private  Context context;
    private List<DataTree<WrongListBean, WrongListBean.DataListBean>> dts;
    public WrongListAdapter(Context context, int type) {
        this.context = context;
        this.type = type;
    }

    public void setData(List<DataTree
            <WrongListBean, WrongListBean.DataListBean>> datas) {

        dts = datas;
        notifyNewData(dts);
    }
    @Override
    public RecyclerView.ViewHolder groupItemViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wronglist_recy_item, parent, false);

        return new WrongListAdapter.GroupItemViewHolder(v);
    }

    @Override
    public RecyclerView.ViewHolder subItemViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wronglist_recy_subitem_layout, parent, false);

        return new WrongListAdapter.SubItemViewHolder(v);
    }

    @Override
    public void onGroupItemBindViewHolder(RecyclerView.ViewHolder holder, int groupItemIndex) {
        WrongListBean groupItem = dts.get(groupItemIndex).getGroupItem();
        ((GroupItemViewHolder) holder).tvGroup.setText(groupItem.getTitle());
        ((GroupItemViewHolder) holder).tv_item_num.setText("共"+groupItem.getDataList().size()+"道");
    }

    @Override
    public void onSubItemBindViewHolder(RecyclerView.ViewHolder holder, int groupItemIndex, int subItemIndex) {
        WrongListBean.DataListBean subitem = dts.get(groupItemIndex).getGroupItem().getDataList().get(groupItemIndex);
        SubItemViewHolder holder1 = (SubItemViewHolder) holder;
        if (type==0)
            holder1.tv_item_type.setText("查看");
        else
            holder1.tv_item_type.setText("练习");
        holder1.tv_item_subitem_title.setText(subitem.getTitle());
        holder1.tv_item_create_time.setText("创建时间:"+subitem.getCreatetime());
        String[] split = subitem.getWrongAnswer().split(",");
        StringBuilder builder = new StringBuilder("");
        for (String s : split) {
            if (s.equals("1"))
                builder.append("A");
            if (s.equals("2"))
                builder.append("B");
            if (s.equals("3"))
                builder.append("C");
            if (s.equals("4"))
                builder.append("D");

        }
        holder1.tv_item_true_question.setText("你的答案:"+builder.toString());
    }

    @Override
    public void onGroupItemClick(Boolean isExpand, WrongListAdapter.GroupItemViewHolder holder, int groupItemIndex) {

    }

    @Override
    public void onSubItemClick(WrongListAdapter.SubItemViewHolder holder, int groupItemIndex, int subItemIndex) {
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i=0;i<dts.size();i++) {
            stringBuilder.append(dts.get(i).getGroupItem().getId()+",");
        }
        Intent intent = new Intent(context, ExerciseTestActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("type",ExerciseTestActivity.COLLE_WRONG_TYPE);
        String s = stringBuilder.toString();
        bundle.putString("ids",s.substring(0,s.length()-1));
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    public static class GroupItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvGroup;
        CheckBox cb_item;
        TextView tv_item_num;

        public GroupItemViewHolder(View itemView) {
            super(itemView);

            tvGroup = (TextView) itemView.findViewById(R.id.tv_group_item);
            cb_item = itemView.findViewById(R.id.cb_item);
            tv_item_num = itemView.findViewById(R.id.tv_item_num);
            Drawable buttonDrawable = cb_item.getCompoundDrawables()[0];
            buttonDrawable.setBounds(0,0, DensityUtil.dp2px(itemView.getContext(),15),DensityUtil.dp2px(itemView.getContext(),15));
            cb_item.setCompoundDrawables(buttonDrawable,null,null,null);
        }
    }

    public static class SubItemViewHolder extends RecyclerView.ViewHolder {


        TextView tv_item_subitem_title;
        TextView tv_item_type;
        TextView tv_item_create_time;
        TextView tv_item_true_question;

        public SubItemViewHolder(View itemView) {
            super(itemView);
            tv_item_subitem_title = itemView.findViewById(R.id.tv_item_subitem_title);
            tv_item_type = itemView.findViewById(R.id.tv_item_type);
            tv_item_create_time = itemView.findViewById(R.id.tv_item_create_time);
            tv_item_true_question = itemView.findViewById(R.id.tv_item_true_question);
        }
    }
}
