package com.hevttc.jdr.interiew.view.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.bean.BaseBean;
import com.hevttc.jdr.interiew.bean.CheckAnswerBean;
import com.hevttc.jdr.interiew.bean.ExeCommitBean;
import com.hevttc.jdr.interiew.bean.ExerciseQuestionBean;
import com.hevttc.jdr.interiew.bean.UserInfoBean;
import com.hevttc.jdr.interiew.util.Constants;
import com.hevttc.jdr.interiew.util.DateUtil;
import com.hevttc.jdr.interiew.util.SPUtils;
import com.hevttc.jdr.interiew.util.StatusBarUtil;
import com.hevttc.jdr.interiew.util.TitleBuilder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hegeyang on 2018/5/2.
 */

public class ExerciseResultActivity extends BaseActivity {
    @BindView(R.id.tv_result_title)
    TextView tvResultTitle;
    @BindView(R.id.tv_result_time)
    TextView tvResultTime;
    @BindView(R.id.tv_result_right)
    TextView tvResultRight;
    @BindView(R.id.tv_result_all)
    TextView tvResultAll;
    @BindView(R.id.rcy_result)
    RecyclerView rcyResult;
    @BindView(R.id.tv_result_goala)
    TextView tvGoAla;
    private ExeCommitBean data;
    private HashMap<Integer, String> answer;
    private BaseBean<ArrayList<CheckAnswerBean>> baseBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exerciseresult;
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setViewTopPadding(this, R.id.top_bar);
        new TitleBuilder(this).setTitleText("结果报告")
                .setLeftIco(R.mipmap.row_back)
                .setLeftIcoListening(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
        data = (ExeCommitBean) getIntent().getExtras().getSerializable("data");
    }

    @Override
    protected void initDatas() {
        tvResultTitle.setText(data.title);
        tvResultTime.setText(DateUtil.getHHMMFromS(data.time));
        answer = data.answer;
        getAnswerData();
    }

    private void getAnswerData() {
        List<ExerciseQuestionBean> data = this.data.baseBean.getData();
        StringBuilder builder = new StringBuilder();
        for (ExerciseQuestionBean temp : data) {
            builder.append(temp.getId()+",");
        }
        String s = builder.toString();
        UserInfoBean signInfo = SPUtils.getSignInfo(mContext);
        OkGo.<String>get(Constants.API_EXECRISE_CHECK_ANSWER)
                .params("uid",signInfo.getId()+"")
                .params("id",s.substring(0,s.length()-1))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Type type = new TypeToken<BaseBean<List<CheckAnswerBean>>>() {
                        }.getType();
                        baseBean = new Gson().fromJson(response.body(), type);
                        if (baseBean.isSuccess()){
                            //Log.e("hgy",baseBean.getData().size()+"");
                            tvResultAll.setText(baseBean.getData().size()+"");
                            parseData(baseBean.getData());

                        }
                    }
                });
    }

    private void parseData(List<CheckAnswerBean> data) {
        rcyResult.setLayoutManager(new GridLayoutManager(mContext,5));
        rcyResult.setAdapter(new ResultAdapter(R.layout.item_exer_card,data));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void initListeners() {
        tvGoAla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("data",baseBean.getData());
                bundle.putInt("index",0);
                bundle.putSerializable("your",answer);
                toActivity(ExerciseAnalysisActivity.class,bundle);
            }
        });
    }

    class ResultAdapter extends BaseQuickAdapter<CheckAnswerBean,BaseViewHolder>{

        public ResultAdapter(@LayoutRes int layoutResId, @Nullable List<CheckAnswerBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, CheckAnswerBean item) {
            ImageView iv_card_item = helper.getView(R.id.iv_card_item);
            TextDrawable drawable;
            if (answer.get(helper.getPosition()).equals(item.getAnswer())){
                drawable = TextDrawable.builder().buildRound(helper.getPosition()+1 + "", getResources().getColor(R.color.title_back));
                tvResultRight.setText(Integer.parseInt(tvResultRight.getText().toString())+1+"");
            }else{
                drawable = TextDrawable.builder().buildRound(helper.getPosition()+1 + "", getResources().getColor(R.color.actionsheet_red));
                if (!TextUtils.isEmpty(answer.get(helper.getPosition()))){
                    commitWrong(item);
                }
            }
            iv_card_item.setBackground(drawable);
        }
    }

    private void commitWrong(CheckAnswerBean item) {
        UserInfoBean signInfo = SPUtils.getSignInfo(mContext);
        OkGo.<String>get(Constants.API_EXEC_USER_COMMIT_WRONG)
                .params("uid",signInfo.getId()+"")
                .params("questionId",item.getQuestionId())
                .params("type",0)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                    }
                });
    }
}
