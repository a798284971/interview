package com.hevttc.jdr.interiew.view.fragment;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.bean.UserInfoBean;
import com.hevttc.jdr.interiew.util.CircleTransform;
import com.hevttc.jdr.interiew.util.DensityUtil;
import com.hevttc.jdr.interiew.util.SPUtils;
import com.hevttc.jdr.interiew.util.StatusBarUtil;
import com.hevttc.jdr.interiew.view.activity.AboutWrongActivity;
import com.hevttc.jdr.interiew.view.activity.ChangeInfoActivity;
import com.hevttc.jdr.interiew.view.activity.CollectActivity;
import com.hevttc.jdr.interiew.view.activity.ReportActivity;
import com.hevttc.jdr.interiew.view.activity.SettingActivity;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hegeyang on 2017/12/21.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {
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
    @BindView(R.id.ll_change_info)
    LinearLayout llChangeInfo;
    @BindView(R.id.rl_browse_wrong)
    RelativeLayout rlBrowseWrong;
    @BindView(R.id.rl_practice_wrong)
    RelativeLayout rlPracticeWrong;
    @BindView(R.id.rl_collect)
    RelativeLayout rlCollect;
    @BindView(R.id.rl_my_sign)
    RelativeLayout rlMySign;
    @BindView(R.id.rl_my_publish)
    RelativeLayout rlMyPublish;
    @BindView(R.id.rl_setting)
    RelativeLayout rlSetting;
    @BindView(R.id.rl_my_report)
    RelativeLayout rlMyReport;
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
    public void onResume() {
        super.onResume();
        UserInfoBean signInfo = SPUtils.getSignInfo(mActivity);
        if(!TextUtils.isEmpty(signInfo.getNickname())) {
            nickname.setText(signInfo.getNickname());
            tvTitle.setText(signInfo.getNickname());
        }
        else {
            nickname.setText("请设置昵称");
            tvTitle.setText("我的");
        }
        if (!TextUtils.isEmpty(signInfo.getEducation()))
            tvEducation.setText(signInfo.getEducation());
        else
            tvEducation.setText("暂未设置学校");
        if (!TextUtils.isEmpty(signInfo.getAvastar())){
            Picasso.with(mActivity).load(signInfo.getAvastar())
                    .transform(new CircleTransform())
                    .into(ivHead);
            Picasso.with(mActivity).load(signInfo.getAvastar())
                    .transform(new CircleTransform())
                    .into(toolbarAvatar);
        }else {
            Picasso.with(mActivity).load(R.mipmap.ic_head_default)
                    .transform(new CircleTransform())
                    .into(ivHead);
            Picasso.with(mActivity).load(R.mipmap.ic_head_default)
                    .transform(new CircleTransform())
                    .into(toolbarAvatar);
        }
    }

    @Override
    protected void initListeners() {
        rlBrowseWrong.setOnClickListener(this);
        rlPracticeWrong.setOnClickListener(this);
        rlCollect.setOnClickListener(this);
        rlSetting.setOnClickListener(this);
        ivDate.setOnClickListener(this);
        rlMyReport.setOnClickListener(this);

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
        rlAllinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toActivity(ChangeInfoActivity.class);
            }
        });
    }

    @Override
    public void initViews(View view) {

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

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()){
            case R.id.rl_browse_wrong:
                bundle.putInt("type",0);
                toActivity(AboutWrongActivity.class,bundle);
                break;
            case R.id.rl_practice_wrong:
                bundle.putInt("type",1);
                toActivity(AboutWrongActivity.class,bundle);
                break;
            case R.id.rl_collect:
                toActivity(CollectActivity.class);
                break;
            case R.id.rl_my_report:
                toActivity(ReportActivity.class);
                break;
            case R.id.rl_setting:
            case R.id.iv_date:
                toActivity(SettingActivity.class);
                break;
        }
    }
}
