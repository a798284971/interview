package com.hevttc.jdr.interiew.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.bean.ExerciesListBean;
import com.hevttc.jdr.interiew.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;



public class ExerciseListAdapter extends SecondaryListAdapter<ExerciseListAdapter.GroupItemViewHolder, ExerciseListAdapter.SubItemViewHolder> {


    private Context context;
    private List<DataTree<ExerciesListBean, ExerciesListBean.DataListBean>> dts;


    public ExerciseListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<SecondaryListAdapter.DataTree
            <ExerciesListBean, ExerciesListBean.DataListBean>> datas) {

        dts = datas;
        notifyNewData(dts);
    }

    @Override
    public RecyclerView.ViewHolder groupItemViewHolder(ViewGroup parent) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exerciselist_recy_item, parent, false);

        return new GroupItemViewHolder(v);
    }

    @Override
    public RecyclerView.ViewHolder subItemViewHolder(ViewGroup parent) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exerciselist_recy_subitem_layout, parent, false);

        return new SubItemViewHolder(v);
    }

    @Override
    public void onGroupItemBindViewHolder(RecyclerView.ViewHolder holder, int groupItemIndex) {

        ExerciesListBean groupItem = dts.get(groupItemIndex).getGroupItem();
        ((GroupItemViewHolder) holder).tvGroup.setText(groupItem.getTitle());

    }

    @Override
    public void onSubItemBindViewHolder(RecyclerView.ViewHolder holder, int groupItemIndex, int subItemIndex) {
        ExerciesListBean.DataListBean dataListBean = dts.get(groupItemIndex).getSubItems().get(subItemIndex);
        //((SubItemViewHolder) holder).tvSub.setText(dataListBean.getTitle());
        SubItemViewHolder holder1 = (SubItemViewHolder) holder;
        holder1.tv_exe_subitem_title.setText(dataListBean.getTitle());
        holder1.tv_exe_subitem_quesnum.setText(dataListBean.getQuestionNum()+"");
        holder1.tv_exe_subitem_studynum.setText(dataListBean.getIsStudyNum()+"");
        if (new Double(dataListBean.getRightRate()).intValue()==0){
            holder1.ll_right_rate.setVisibility(View.INVISIBLE);
        }else{
            holder1.ll_right_rate.setVisibility(View.VISIBLE);
            holder1.tv_exe_subitem_rightRate.setText(dataListBean.getRightRate()+"");
        }
    }

    @Override
    public void onGroupItemClick(Boolean isExpand, GroupItemViewHolder holder, int groupItemIndex) {
        holder.cb_item.setChecked(!holder.cb_item.isChecked());
    }

    @Override
    public void onSubItemClick(SubItemViewHolder holder, int groupItemIndex, int subItemIndex) {

    }

    public static class GroupItemViewHolder extends RecyclerView.ViewHolder {


        TextView tvGroup;
        CheckBox cb_item;

        public GroupItemViewHolder(View itemView) {
            super(itemView);

           tvGroup = (TextView) itemView.findViewById(R.id.tv_group_item);
            cb_item = itemView.findViewById(R.id.cb_item);
            Drawable buttonDrawable = cb_item.getCompoundDrawables()[0];
            buttonDrawable.setBounds(0,0, DensityUtil.dp2px(itemView.getContext(),15),DensityUtil.dp2px(itemView.getContext(),15));
            cb_item.setCompoundDrawables(buttonDrawable,null,null,null);
        }
    }

    public static class SubItemViewHolder extends RecyclerView.ViewHolder {


        TextView tv_exe_subitem_title;
        TextView tv_exe_subitem_studynum;
        TextView tv_exe_subitem_quesnum;
        TextView tv_exe_subitem_rightRate;
        LinearLayout ll_right_rate;

        public SubItemViewHolder(View itemView) {
            super(itemView);

            tv_exe_subitem_title = itemView.findViewById(R.id.tv_exe_subitem_title);
            tv_exe_subitem_quesnum = itemView.findViewById(R.id.tv_exe_subitem_quesnum);
            tv_exe_subitem_studynum = itemView.findViewById(R.id.tv_exe_subitem_studynum);
            ll_right_rate = itemView.findViewById(R.id.ll_right_rate);
            tv_exe_subitem_rightRate = itemView.findViewById(R.id.tv_exe_subitem_rightRate);
        }
    }


}

