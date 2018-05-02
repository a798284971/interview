package com.hevttc.jdr.interiew.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.view.activity.ExerciseTestActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hegeyang on 2018/4/25.
 */

public class ExeCardFragment extends BaseFragment {
    @BindView(R.id.tv_single_choose_title)
    TextView tvSingleChooseTitle;
    @BindView(R.id.rcy_execard)
    RecyclerView rcyExecard;
    Unbinder unbinder;
    private ExerciseTestActivity exerciseTestActivity;
    private ExerCardAdapter exerCardAdapter;
    protected boolean isCreate = false;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_execard;
    }

    @Override
    public void initDatas() {
        exerciseTestActivity = (ExerciseTestActivity) this.mActivity;
        HashMap<Integer, String> chooseItem = exerciseTestActivity.getChooseItem();
        ArrayList<String> datas = new ArrayList<>();
        for (int i =0;i<exerciseTestActivity.getItemCout();i++) {
            datas.add(chooseItem.get(i));
            Log.e("hgy",i+"----"+chooseItem.get(i));
        }
        exerCardAdapter = new ExerCardAdapter(R.layout.item_exer_card, datas);
        for (String s:datas
             ) {
            //Log.e("hgy",s+datas.size());
        }
        rcyExecard.setAdapter(exerCardAdapter);
        exerCardAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                exerciseTestActivity.setVpCurrentItem(position);
            }
        });
    }

    @Override
    public void initViews(View view) {
        rcyExecard.setLayoutManager(new GridLayoutManager(mActivity,5));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreate = true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onResume() {
        super.onResume();
        //initDatas();
    }

    class ExerCardAdapter extends BaseQuickAdapter<String,BaseViewHolder>{

        public ExerCardAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            ImageView iv_card_item = helper.getView(R.id.iv_card_item);
            TextDrawable drawable;
            if (TextUtils.isEmpty(item)) {
                drawable = TextDrawable.builder().buildRound(helper.getPosition()+1 + "", getResources().getColor(R.color.divider));
            }
            else
                drawable =TextDrawable.builder().buildRound(helper.getPosition()+1+"", getResources().getColor(R.color.light_green));
            iv_card_item.setBackground(drawable);
        }
    }
}
