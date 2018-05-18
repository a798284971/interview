package com.hevttc.jdr.interiew.view.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.adapter.CommentListAdapter;
import com.hevttc.jdr.interiew.bean.BaseBean;
import com.hevttc.jdr.interiew.bean.CommentListBean;
import com.hevttc.jdr.interiew.bean.SignTalkBean;
import com.hevttc.jdr.interiew.bean.UserInfoBean;
import com.hevttc.jdr.interiew.util.CircleTransform;
import com.hevttc.jdr.interiew.util.Constants;
import com.hevttc.jdr.interiew.util.SPUtils;
import com.hevttc.jdr.interiew.util.StatusBarUtil;
import com.hevttc.jdr.interiew.util.TitleBuilder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 评论页面
 */

public class CommentActivity extends BaseActivity {
    @BindView(R.id.tv_comment_item_nick)
    TextView tv_comment_item_nick;
    @BindView(R.id.tv_comment_item_content)
    TextView tv_comment_item_content;
    @BindView(R.id.tv_comment_item_time)
    TextView tv_comment_item_time;
    @BindView(R.id.iv_comment_item_head)
    ImageView iv_comment_item_head;
    @BindView(R.id.rec_comment)
    RecyclerView rec_comment;
    @BindView(R.id.sf_comment)
    SwipeRefreshLayout sfComment;
    @BindView(R.id.dialog_comment_bt)
    TextView dialog_comment_bt;
    @BindView(R.id.dialog_comment_et)
    EditText dialog_comment_et;
    private SignTalkBean data;
    private BaseBean<List<CommentListBean.DataBean>> baseBean;
    private CommentListAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_comment;
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        StatusBarUtil.setViewTopPadding(this, R.id.top_bar);
        TitleBuilder titleBuilder = new TitleBuilder(this)
                .setLeftIco(R.mipmap.row_back)
                .setLeftIcoListening(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
        titleBuilder.setTitleText("详情评论");
        sfComment.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉进度的主题颜色
        sfComment.setColorSchemeResources(R.color.colorAccent);
        sfComment.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromNet();
            }
        });
        rec_comment.setLayoutManager(new LinearLayoutManager(this));
        dialog_comment_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTalk();

            }
        });

    }

    private void sendTalk() {
        String sendData = dialog_comment_et.getText().toString().trim();
        if (TextUtils.isEmpty(sendData)){
            Toast.makeText(mContext, "请输入评论内容", Toast.LENGTH_SHORT).show();
            return;
        }

        UserInfoBean signInfo = SPUtils.getSignInfo(mContext);
        OkGo.<String>get(Constants.API_SIGN_COMMENT)
                .params("uid",signInfo.getId())
                .params("talkId",data.getTalkId())
                .params("text",sendData)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if(jsonObject.getBoolean("success")) {
                                getDataFromNet();
                                Toast.makeText(mContext, "评论成功", Toast.LENGTH_SHORT).show();
                                dialog_comment_et.setText("");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(mContext, "评论失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Toast.makeText(mContext, "评论失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void initDatas() {
        data = (SignTalkBean) getIntent().getExtras().getSerializable("data");
        tv_comment_item_nick.setText(data.getNickName());
        tv_comment_item_time.setText(data.getCreateTime());
        tv_comment_item_content.setText(data.getTalkText());
        if (!TextUtils.isEmpty((CharSequence) data.getHeadImg())) {
            Picasso.with(this).load((String) data.getHeadImg())
                    .transform(new CircleTransform())
                    .into(iv_comment_item_head);
        } else {
            Picasso.with(mContext).load(R.mipmap.ic_head_default)
                    .transform(new CircleTransform())
                    .into(iv_comment_item_head);
        }
       getDataFromNet();
    }

    private void getDataFromNet() {
        OkGo.<String>get(Constants.API_SIGN_COMMENT_LIST)
                .params("talkId", data.getTalkId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Type type = new TypeToken<BaseBean<List<CommentListBean.DataBean>>>() {
                        }.getType();
                        baseBean = new Gson().fromJson(response.body(), type);
                        if (baseBean.isSuccess()) {
                            if (adapter == null) {
                                adapter = new CommentListAdapter(R.layout.item_comment_list, baseBean.getData());
                                rec_comment.setAdapter(adapter);
                                //Log.d("jdr","----------======"+ response.body());
                            }else{
                                adapter.replaceData(baseBean.getData());
                                adapter.notifyDataSetChanged();
                                sfComment.setRefreshing(false);
                            }
                        }
                    }
                });
    }

}

