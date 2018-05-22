package com.hevttc.jdr.interiew.view.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.bean.BaseBean;
import com.hevttc.jdr.interiew.bean.ExeCommitBean;
import com.hevttc.jdr.interiew.bean.ExerciseQuestionBean;
import com.hevttc.jdr.interiew.bean.UserInfoBean;
import com.hevttc.jdr.interiew.util.Constants;
import com.hevttc.jdr.interiew.util.DateUtil;
import com.hevttc.jdr.interiew.util.SPUtils;
import com.hevttc.jdr.interiew.util.StatusBarUtil;
import com.hevttc.jdr.interiew.view.fragment.BaseFragment;
import com.hevttc.jdr.interiew.view.fragment.ExeCardFragment;
import com.hevttc.jdr.interiew.view.fragment.choose_fragment.MoreChooseFragment;
import com.hevttc.jdr.interiew.view.fragment.choose_fragment.SingleChooseFragment;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hegeyang on 2018/4/9.
 */

public class ExerciseTestActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.bt_exer_title)
    Button btExerTitle;
    @BindView(R.id.iv_exer_card)
    ImageView ivExerCard;
    @BindView(R.id.tv_exer_time)
    TextView tvExerTime;
    @BindView(R.id.vp_exer_main)
    ViewPager vpExerMain;
    @BindView(R.id.tv_exer_last)
    TextView tvExerLast;
    @BindView(R.id.tv_exer_next)
    TextView tvExerNext;
    private int superioe;
    private ArrayList<BaseFragment> exerFragmentList;
    private BaseBean<List<ExerciseQuestionBean>> baseBean;
    private HashMap<Integer, String> chooseItems;
    private Handler handler = new Handler();
    private int recLen = 0;
    public static final int PRACTICE_TYPE = 0x000001;
    public static final int EXAM_TYPE = 0x00002;
    public static final int COLLE_WRONG_TYPE = 0x000003;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_exercise_test;
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setViewTopPadding(this, R.id.rl_title);
        tvExerLast.setEnabled(false);
    }

    @Override
    protected void initDatas() {
        int type = getIntent().getIntExtra("type", 0);
        if (type==PRACTICE_TYPE) {
            superioe = getIntent().getIntExtra("superioe", 0);
            String title = getIntent().getStringExtra("title");
            btExerTitle.setText(title + "专项练习");
            getExerData();
        }else if(type==EXAM_TYPE){
            btExerTitle.setText("在线测试");
            getExamData();
        }else{
            btExerTitle.setText("专项练习");
            String ids = getIntent().getStringExtra("ids");
            getSomeQuestionData(ids);
        }
        chooseItems = new HashMap<Integer,String>();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                recLen++;
                String hhmmFromS = DateUtil.getHHMMFromS(recLen);
                tvExerTime.setText(hhmmFromS);
                handler.postDelayed(this, 1000);
            }
        },1000);
    }
    //错题或者收藏
    private void getSomeQuestionData(String ids) {
        OkGo.<String>get(Constants.API_EXECRISE_GETSOMEQUESTION)
                .params("id",ids)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Type type = new TypeToken<BaseBean<List<ExerciseQuestionBean>>>() {
                        }.getType();
                        //Log.e("hgy", response.body().toString() );
                        baseBean = new Gson().fromJson(response.body(), type);
                        if (baseBean.isSuccess()){

                            initViewPager(baseBean.getData());
                        }else
                            Toast.makeText(mContext, "失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Toast.makeText(mContext, "失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //组卷考试
    private void getExamData() {
        UserInfoBean info = SPUtils.getSignInfo(mContext);
        OkGo.<String>get(Constants.API_EXECRISE_GETEXEAM_QUESTION)
                .params("uid",info.getId()+"")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Type type = new TypeToken<BaseBean<List<ExerciseQuestionBean>>>() {
                        }.getType();
                        //Log.e("hgy", response.body().toString() );
                        baseBean = new Gson().fromJson(response.body(), type);
                        if (baseBean.isSuccess()){

                            initViewPager(baseBean.getData());
                        }else
                            Toast.makeText(mContext, "失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Toast.makeText(mContext, "失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }
        //专项练习
    private void getExerData() {
        //Toast.makeText(mContext, "superioe--"+superioe, Toast.LENGTH_SHORT).show();
        UserInfoBean info = SPUtils.getSignInfo(mContext);
        OkGo.<String>get(Constants.API_EXECRISE_PRACTICE)
                .params("uid",info.getId()+"")
                .params("superioe",superioe+"")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Type type = new TypeToken<BaseBean<List<ExerciseQuestionBean>>>() {
                        }.getType();
                        //Log.e("hgy", response.body().toString() );
                        baseBean = new Gson().fromJson(response.body(), type);
                        if (baseBean.isSuccess()){

                            initViewPager(baseBean.getData());
                        }else
                            Toast.makeText(mContext, "失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Toast.makeText(mContext, "失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initViewPager(List<ExerciseQuestionBean> data) {
        int intTemp = 0;
        exerFragmentList = new ArrayList<BaseFragment>();
        for (ExerciseQuestionBean bean : data) {
           if (bean.getType()==0)
               exerFragmentList.add(new SingleChooseFragment(bean,intTemp+1));
            else
                exerFragmentList.add(new MoreChooseFragment(bean,intTemp+1));
            chooseItems.put(intTemp++,"");
        }
        exerFragmentList.add(new ExeCardFragment());
        //vpExerMain.setOffscreenPageLimit(exerFragmentList.size());
        vpExerMain.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return exerFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return exerFragmentList.size();
            }
        });
    }

    @Override
    protected void initListeners() {
        btExerTitle.setOnClickListener(this);
        tvExerLast.setOnClickListener(this);
        tvExerNext.setOnClickListener(this);
        ivExerCard.setOnClickListener(this);
        vpExerMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @SuppressWarnings("Since15")
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    if (position<exerFragmentList.size()-1){
                        if (baseBean.getData().get(position).getType()==0){
                            SingleChooseFragment singleChooseFragment = (SingleChooseFragment) exerFragmentList.get(position);

                            //chooseItems.replace(position,(singleChooseFragment.getChooseItem()));
                            chooseItems.put(position,singleChooseFragment.getChooseItem());

                        }else{
                            MoreChooseFragment moreChooseFragment = (MoreChooseFragment) exerFragmentList.get(position);
                            chooseItems.put(position,moreChooseFragment.getChooseItem());
                            //Log.e("hgy",position+"----"+moreChooseFragment.getChooseItem());
                        }
                    }
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0){
                    tvExerLast.setEnabled(false);
                    tvExerNext.setEnabled(true);
                    tvExerNext.setText("下一题");
                    tvExerLast.setText("上一题");
                }else if(position==exerFragmentList.size()-2){
                    tvExerNext.setText("查看答题卡");
                    tvExerLast.setEnabled(true);
                    tvExerNext.setEnabled(true);
                    tvExerLast.setText("上一题");
                }else if(position==exerFragmentList.size()-1){
                    tvExerNext.setText("提交");
                    tvExerLast.setText("上一步");
                    tvExerLast.setEnabled(true);
                    tvExerNext.setEnabled(true);
                    exerFragmentList.get(position).initDatas();
                }
                else{
                    tvExerNext.setText("下一题");
                    tvExerLast.setText("上一题");
                    tvExerLast.setEnabled(true);
                    tvExerNext.setEnabled(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_exer_title:
                finish();
                break;
            case R.id.tv_exer_last:
                vpExerMain.setCurrentItem(vpExerMain.getCurrentItem()-1);
                break;
            case R.id.tv_exer_next:
                if (!tvExerNext.getText().toString().equals("提交"))
                    vpExerMain.setCurrentItem(vpExerMain.getCurrentItem()+1);
                else
                    showCommitDialog();
                break;
            case R.id.iv_exer_card:
                vpExerMain.setCurrentItem(exerFragmentList.size()-1);
                exerFragmentList.get(exerFragmentList.size()-1).initDatas();
                break;

        }
    }

    private void showCommitDialog() {
        new AlertDialog.Builder(mContext)
                .setMessage("已经到最后了，是否提交答案")
                .setNegativeButton("取消", null)
                .setPositiveButton("提交", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        commitQuestion();
                    }
                })
                .show();
    }

    private void commitQuestion() {
        Bundle bundle = new Bundle();
        ExeCommitBean exeCommitBean = new ExeCommitBean();
        exeCommitBean.answer = chooseItems;
        exeCommitBean.baseBean = baseBean;
        exeCommitBean.time = recLen;
        exeCommitBean.title = btExerTitle.getText().toString();
        bundle.putSerializable("data",exeCommitBean);
        toActivity(ExerciseResultActivity.class,bundle);
        handler.removeCallbacksAndMessages(null);
        finish();
    }

    public int getItemCout(){
        return exerFragmentList.size()-1;
    }
    public  HashMap<Integer,String> getChooseItem(){
        return chooseItems;
    }
    public void setVpCurrentItem(int position){
        vpExerMain.setCurrentItem(position);
    }
}
