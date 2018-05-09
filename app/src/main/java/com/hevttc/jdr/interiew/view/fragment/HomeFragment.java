package com.hevttc.jdr.interiew.view.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.adapter.SignListAdapter;
import com.hevttc.jdr.interiew.bean.BaseBean;
import com.hevttc.jdr.interiew.bean.SignTalkBean;
import com.hevttc.jdr.interiew.bean.UserInfoBean;
import com.hevttc.jdr.interiew.util.Constants;
import com.hevttc.jdr.interiew.util.SPUtils;
import com.hevttc.jdr.interiew.util.StatusBarUtil;
import com.hevttc.jdr.interiew.util.TitleBuilder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by hegeyang on 2017/12/21.
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.rcy_home)
    RecyclerView rcyHome;
    Unbinder unbinder;
    @BindView(R.id.sf_home)
    SwipeRefreshLayout sfHome;
    private SignListAdapter signListAdapter;
    private BaseBean<List<SignTalkBean>> baseBean;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initDatas() {
        getDataFromNet();
    }

    private void getDataFromNet() {
        UserInfoBean signInfo = SPUtils.getSignInfo(mActivity);
        OkGo.<String>get(Constants.API_SIGN_LIST)
                .params("uid",signInfo.getId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Type type = new TypeToken<BaseBean<List<SignTalkBean>>>() {
                        }.getType();
                        //Log.e("hgy", response.body().toString() );
                        baseBean = new Gson().fromJson(response.body(), type);
                        if (baseBean.isSuccess()){
                            if (signListAdapter==null) {
                                signListAdapter = new SignListAdapter(R.layout.item_sign_list, baseBean.getData());
                                rcyHome.setAdapter(signListAdapter);
                            }else{
                                signListAdapter.replaceData(baseBean.getData());
                                signListAdapter.notifyDataSetChanged();
                                sfHome.setRefreshing(false);
                            }
                            signListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                                @Override
                                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                                    switch (view.getId()){
                                        case R.id.cb_item_share:
                                            showShare();
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        sfHome.setRefreshing(false);
                    }
                });
    }

    @Override
    public void initViews(View view) {
        new TitleBuilder(mActivity, view)
                .setTitleText("社区");
        StatusBarUtil.setViewTopPadding(mActivity, view, R.id.top_bar);
        sfHome.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉进度的主题颜色
        sfHome.setColorSchemeResources(R.color.colorAccent);
        sfHome.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromNet();
            }
        });
        rcyHome.setLayoutManager(new LinearLayoutManager(mActivity));

    }

    @Override
    protected void initListeners() {
        super.initListeners();
        //if (signListAdapter!=null)

    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("测试");
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("这是一个分享");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
       // oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl("http://www.baidu.com");
        // comment是我对这条分享的评论，仅在人人网使用
        //oks.setComment("我是测试评论文本");
        // 启动分享GUI
        oks.show(mActivity);
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
