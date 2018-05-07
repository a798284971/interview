package com.hevttc.jdr.interiew.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.bean.CheckAnswerBean;
import com.hevttc.jdr.interiew.util.StatusBarUtil;
import com.hevttc.jdr.interiew.view.customview.NoCacheViewPager;
import com.hevttc.jdr.interiew.view.fragment.BaseFragment;
import com.hevttc.jdr.interiew.view.fragment.choose_fragment.AnalysisFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hegeyang on 2018/5/2.
 */

public class ExerciseAnalysisActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.bt_analy_title)
    Button btAnalyTitle;
    @BindView(R.id.cb_analy_collect)
    CheckBox cbAnalyCollect;
    @BindView(R.id.iv_analy_share)
    ImageView ivAnalyShare;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.vp_analysis)
    NoCacheViewPager vpAnalysis;
    private ArrayList<CheckAnswerBean> data;
    private ArrayList<BaseFragment> fragments;
    private int goIndex;
    private HashMap<Integer, String> your;
    private AlertDialog alertDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exercise_analysis;
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setViewTopPadding(this, R.id.rl_title);
        Bundle extras = getIntent().getExtras();
        data = extras.getParcelableArrayList("data");
        goIndex = extras.getInt("index");
        your = (HashMap<Integer, String>) extras.getSerializable("your");
    }


    @Override
    protected void initDatas() {
        initViewPager();
    }

    private void initViewPager() {
        int intTemp = 0;
        fragments = new ArrayList<>();
        for (CheckAnswerBean bean :data) {
            fragments.add(new AnalysisFragment(bean,intTemp+1,your.get(intTemp)));
            intTemp++;
        }
        vpAnalysis.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        vpAnalysis.setCurrentItem(goIndex);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        btAnalyTitle.setOnClickListener(this);
        vpAnalysis.setOnSideListener(new NoCacheViewPager.onSideListener() {
            @Override
            public void onLeftSide() {

            }

            @Override
            public void onRightSide() {
                showOutDialog();
            }
        });
    }

    private void showOutDialog() {
        if (alertDialog==null){
            alertDialog = new AlertDialog.Builder(mContext)
                    .setMessage("已经没有后面了，你接下来要做什么")
                    .setNegativeButton("再看看", null)
                    .setPositiveButton("回到首页", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(mContext, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }).create();
            alertDialog.show();
        }else{
            if (!alertDialog.isShowing())
                alertDialog.show();
        }
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
            case R.id.bt_analy_title:
                finish();
                break;
        }
    }
    public int getItemCout(){
        return fragments.size();
    }
    public void isCollect(boolean falg){
        cbAnalyCollect.setChecked(falg);
    }
}
