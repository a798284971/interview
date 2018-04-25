package com.hevttc.jdr.interiew.view.fragment;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.util.CircleTransform;
import com.hevttc.jdr.interiew.util.DensityUtil;
import com.hevttc.jdr.interiew.util.StatusBarUtil;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hegeyang on 2017/12/21.
 */

public class MineFragment extends BaseFragment {
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.nickname)
    TextView nickname;
    @BindView(R.id.rl_allinfo)
    RelativeLayout rlAllinfo;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.toolbar_avatar)
    ImageView toolbarAvatar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.buttonBarLayout)
    ButtonBarLayout buttonBarLayout;
    @BindView(R.id.iv_date)
    ImageView ivDate;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    Unbinder unbinder;
    @BindView(R.id.tv_education)
    TextView tvEducation;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.ll_change_info)
    LinearLayout llChangeInfo;
    private int mOffset = 0;
    boolean isblack = false;//状态栏字体是否是黑色
    boolean iswhite = true;//状态栏字体是否是亮色

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initDatas() {

    }

    @Override
    protected void initListeners() {


        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //200是appbar的高度
                if (Math.abs(verticalOffset) == DensityUtil.dp2px(mActivity, 220) - toolbar.getHeight()) {//关闭
                    if (iswhite) {//变黑
                        isblack = true;
                        iswhite = false;

                    }
                    buttonBarLayout.setVisibility(View.VISIBLE);
                    collapsingToolbar.setContentScrimResource(R.color.white);
                    ivBack.setBackgroundResource(R.mipmap.back_black);
                    ivDate.setBackgroundResource(R.mipmap.date_black);
                } else {  //展开
                    if (isblack) { //变白
                        isblack = false;
                        iswhite = true;
                    }
                    buttonBarLayout.setVisibility(View.INVISIBLE);
                    collapsingToolbar.setContentScrimResource(R.color.transparent);
                    ivBack.setBackgroundResource(R.mipmap.back_white);
                    ivDate.setBackgroundResource(R.mipmap.date_white);

                }
            }
        });
    }

    @Override
    public void initViews(View view) {
        Picasso.with(mActivity).load(R.mipmap.ic_default)
                .transform(new CircleTransform())
                .into(ivHead);
        Picasso.with(mActivity).load(R.mipmap.ic_default)
                .transform(new CircleTransform())
                .into(toolbarAvatar);
        StatusBarUtil.setViewTopPadding(mActivity, view, R.id.toolbar);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
