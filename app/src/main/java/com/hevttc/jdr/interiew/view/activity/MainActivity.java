package com.hevttc.jdr.interiew.view.activity;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.adapter.MainViewPagerAdapter;
import com.hevttc.jdr.interiew.util.DensityUtil;
import com.hevttc.jdr.interiew.view.customview.NoScrollViewPager;
import com.hevttc.jdr.interiew.view.fragment.HomeFragment;
import com.hevttc.jdr.interiew.view.fragment.MessageFragment;
import com.hevttc.jdr.interiew.view.fragment.MineFragment;
import com.hevttc.jdr.interiew.view.fragment.StudyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {


    @BindView(R.id.rb_study)
    RadioButton rbStudy;
    @BindView(R.id.rb_find_home)
    RadioButton rbFindHome;
    @BindView(R.id.rb_mine)
    RadioButton rbMine;
    @BindView(R.id.rg_main)
    RadioGroup rgMain;
    private NoScrollViewPager vpMain;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        vpMain = findViewById(R.id.vp_main);
        RadioButton[] rb = {rbStudy,rbFindHome,rbMine};
        for (int i = 0; i < rb.length; i++) {
            //挨着给每个RadioButton加入drawable限制边距以控制显示大小
            Drawable[] drawables = rb[i].getCompoundDrawables();
            //获取drawables，2/5表示图片要缩小的比例
            Rect r = new Rect(0, 0, DensityUtil.dp2px(mContext,20),DensityUtil.dp2px(mContext,20));
            //定义一个Rect边界
            drawables[1].setBounds(r);
            //给每一个RadioButton设置图片大小
            rb[i].setCompoundDrawables(null, drawables[1], null, null);
        }
    }

    @Override
    protected void initDatas() {
        initViewPagers();
    }

    private void initViewPagers() {
        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(new StudyFragment());
        fragments.add(new HomeFragment());
        fragments.add(new MineFragment());
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragments);
        vpMain.setAdapter(adapter);

        /*禁止滑动*/
        vpMain.setScroll(false);
        /*增加缓存页面数量*/
        vpMain.setOffscreenPageLimit(fragments.size() - 1);

        /*默认选中第一个选项卡*/
        rgMain.check(R.id.rb_study);
    }

    @Override
    protected void initListeners() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rb_study:
                        vpMain.setCurrentItem(0,false);
                        break;
                    case R.id.rb_find_home:
                        vpMain.setCurrentItem(1,false);
                        break;
                    case R.id.rb_mine:
                        vpMain.setCurrentItem(2,false);
                        break;
                }
            }
        });
    }

    //切换导航栏
    public void setViewPager(int position) {
        rgMain.check(rgMain.getChildAt(position).getId());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
