package com.hevttc.jdr.interiew.view.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.adapter.RvDividerItemDecoration;
import com.hevttc.jdr.interiew.adapter.UserCollectAdapter;
import com.hevttc.jdr.interiew.bean.BaseBean;
import com.hevttc.jdr.interiew.bean.UserCollectBean;
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

/**
 * Created by hegeyang on 2018/4/28.
 */

public class CollectActivity extends BaseActivity {
    @BindView(R.id.title_leftIco)
    ImageView titleLeftIco;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.title_rightIco)
    ImageView titleRightIco;
    @BindView(R.id.title_bar)
    RelativeLayout titleBar;
    @BindView(R.id.top_bar)
    LinearLayout topBar;
    @BindView(R.id.rcy_collect)
    RecyclerView rcyCollect;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collect;
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setViewTopPadding(this, R.id.top_bar);
        new TitleBuilder(this).setLeftIco(R.mipmap.row_back)
                .setLeftIcoListening(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).setTitleText("我收藏的题目");
    }

    @Override
    protected void initDatas() {
        getDataFromNet();
    }

    private void getDataFromNet() {
        UserInfoBean signInfo = SPUtils.getSignInfo(mContext);
        OkGo.<String>get(Constants.API_EXEC_USER_COLLECT)
                .params("uid",signInfo.getId())
                .params("type","0")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Type type = new TypeToken<BaseBean<List<UserCollectBean>>>() {
                        }.getType();
                        final BaseBean<List<UserCollectBean>> baseBean = new Gson().fromJson(response.body(), type);
                        if (baseBean.isSuccess()){
                            rcyCollect.setLayoutManager(new LinearLayoutManager(mContext));
                            rcyCollect.addItemDecoration(new RvDividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
                            UserCollectAdapter userCollectAdapter = new UserCollectAdapter(R.layout.item_user_collect, baseBean.getData());
                            rcyCollect.setAdapter(userCollectAdapter);
                            userCollectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    StringBuilder stringBuilder = new StringBuilder("");
                                    for (UserCollectBean bean : baseBean.getData()) {
                                        stringBuilder.append(bean.getId()+",");
                                    }
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("type",ExerciseTestActivity.COLLE_WRONG_TYPE);
                                    String s = stringBuilder.toString();
                                    bundle.putString("ids",s.substring(0,s.length()-1));
                                    toActivity(ExerciseTestActivity.class,bundle);
                                }
                            });
                        }
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
