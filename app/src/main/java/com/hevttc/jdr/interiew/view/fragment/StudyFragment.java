package com.hevttc.jdr.interiew.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.bean.BaseBean;
import com.hevttc.jdr.interiew.bean.LunboBean;
import com.hevttc.jdr.interiew.bean.MessageBean;
import com.hevttc.jdr.interiew.util.Constants;
import com.hevttc.jdr.interiew.util.StatusBarUtil;
import com.hevttc.jdr.interiew.view.customview.Gradient;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.squareup.picasso.Picasso;
import com.sunfusheng.marqueeview.MarqueeView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by hegeyang on 2017/12/21.
 */

public class StudyFragment extends BaseFragment {
    @BindView(R.id.top_bar)
    RelativeLayout topBar;
    @BindView(R.id.gradient)
    Gradient gradient;
    @BindView(R.id.marqueeView)
    MarqueeView marqueeView;
    @BindView(R.id.tv_home_clicktosee_detail)
    TextView tvHomeClicktoseeDetail;
    @BindView(R.id.linear_notify)
    LinearLayout linearNotify;
    @BindView(R.id.scroll_home)
    NestedScrollView scrollView;
    @BindView(R.id.tv_study_nowdate)
    TextView tvStudyNowdate;
    @BindView(R.id.ll_study_signUp)
    LinearLayout llStudySignUp;
    @BindView(R.id.tv_study_questionNum)
    TextView tvStudyQuestionNum;
    @BindView(R.id.tv_study_studyNum)
    TextView tvStudyStudyNum;
    @BindView(R.id.tv_study_signNum)
    TextView tvStudySignNum;
    Unbinder unbinder;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_study;
    }

    @Override
    public void initDatas() {
        initMarquee();
        initText();
        //initUserStatus();
    }


    @Override
    public void initViews(View view) {
        StatusBarUtil.setViewTopPadding(mActivity, view, R.id.top_bar);
        final int height = topBar.getHeight();
        view.setMinimumHeight(height);
    }

    private void initMarquee() {
        OkGo.<String>get(Constants.API_LUNBO)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("hgy", response.body());
                        Type type = new TypeToken<BaseBean<List<LunboBean>>>() {
                        }.getType();
                        BaseBean<List<LunboBean>> baseBean = new Gson().fromJson(response.body(), type);
                        if (baseBean.isSuccess()) {
                            List<ImageView> marqueeImage = new ArrayList<>();
                            for (LunboBean bean : baseBean.getData()) {
                                ImageView imageView = new ImageView(mActivity);
                                Picasso.with(mActivity).load(bean.getImgsrc())
                                        .placeholder(R.mipmap.ic_default)
                                        .error(R.mipmap.ic_default)
                                        .fit()
                                        .into(imageView);
                                marqueeImage.add(imageView);
                            }
                            gradient.setImageViews(marqueeImage);
                        }

                    }
                });
    }

    private void initText() {
        OkGo.<String>get(Constants.API_MESSAGE)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Type type = new TypeToken<BaseBean<List<MessageBean>>>() {
                        }.getType();
                        BaseBean<List<MessageBean>> baseBean = new Gson().fromJson(response.body(), type);
                        if (baseBean.isSuccess()) {

                        }
                        List<String> textList = new ArrayList();
                        for (MessageBean bean : baseBean.getData()) {
                            textList.add(bean.getTitle());
                        }
                        marqueeView.startWithList(textList);
                    }
                });
    }
    public void initUserStatus(){
        OkGo.<String>get(Constants.API_MESSAGE)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Type type = new TypeToken<BaseBean<List<MessageBean>>>() {
                        }.getType();
                        BaseBean<List<MessageBean>> baseBean = new Gson().fromJson(response.body(), type);
                        if (baseBean.isSuccess()) {

                        }
                        List<String> textList = new ArrayList();
                        for (MessageBean bean : baseBean.getData()) {
                            textList.add(bean.getTitle());
                        }
                        marqueeView.startWithList(textList);
                    }
                });
    }

    @Override
    protected void initListeners() {
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                /*以图片为基准，超过图片高度则固定颜色*/
                if (scrollY >= gradient.getTop() + gradient.getMeasuredHeight()) {
                    topBar.setBackgroundColor(getResources().getColor(R.color.title_back));
                    /*其余情况动态计算百分比改变颜色*/
                } else if (scrollY >= 0) {
                    //计算透明度，滑动到的距离（即当前滑动坐标）/图片高度（底部坐标）
                    float persent = scrollY * 1f / (gradient.getTop() + gradient.getMeasuredHeight());
                    //255==1，即不透明，计算动态透明度
                    int alpha = (int) (255 * persent);
                    //计算颜色值，将16进制颜色值转换为rgb颜色后填入
                    int color = Color.argb(alpha, 4, 75, 81);
                    //动态设置
                    topBar.setBackgroundColor(color);
                }
            }
        });
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
