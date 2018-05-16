package com.hevttc.jdr.interiew.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.adapter.GuidePageAdapter;
import com.hevttc.jdr.interiew.util.Constants;
import com.hevttc.jdr.interiew.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018-5-15.
 */

public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.guide_tv_start)
    TextView tv_start;
    @BindView(R.id.guide_ll_point)
    ViewGroup vg;
    @BindView(R.id.guide_vp)
    ViewPager vp;
    private int []imageIdArray;//图片资源的数组
    private List<View> viewList;//图片资源的集合
    //实例化原点View
    private ImageView iv_point;
    private ImageView []ivPointArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        //设置引导页按钮的单击事件--跳转到登录页
        tv_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.getBoolean(GuideActivity.this, Constants.REME_FIRST,true);
                Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initViews() {
        //加载ViewPager
        initViewPager();
        //加载底部圆点
        initPoint();
    }

    @Override
    protected void initDatas() {

    }
    /**
     * 加载底部圆点
     */
    private void initPoint() {
        //这里实例化LinearLayout
        vg = (ViewGroup) findViewById(R.id.guide_ll_point);
        //根据ViewPager的item数量实例化数组
        ivPointArray = new ImageView[viewList.size()];
        //循环新建底部圆点ImageView，将生成的ImageView保存到数组中
        int size = viewList.size();
        for (int i = 0;i<size;i++){
            iv_point = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(30,30));
            layoutParams.leftMargin = 20;
            layoutParams.rightMargin = 20;
            ivPointArray[i] = iv_point;
            //第一个页面需要设置为选中状态，这里采用两张不同的图片
            if (i == 0){
                iv_point.setBackgroundResource(R.drawable.point_focus);
            }else{
                iv_point.setBackgroundResource(R.drawable.point_normal);
            }
            //将数组中的ImageView加入到ViewGroup
            vg.addView(ivPointArray[i]);
            iv_point.setLayoutParams(layoutParams);
        }
    }
    /**
     * 加载图片ViewPager
     */
    private void initViewPager() {
        vp = (ViewPager) findViewById(R.id.guide_vp);
        //实例化图片资源
        imageIdArray = new int[]{R.mipmap.guide_1,R.mipmap.guide_2,R.mipmap.guide_3};
        viewList = new ArrayList<>();
        //获取一个Layout参数，设置为全屏
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        //循环创建View并加入到集合中
        int len = imageIdArray.length;
        for (int i = 0;i<len;i++){
            //new ImageView并设置全屏和图片资源
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setBackgroundResource(imageIdArray[i]);
            //将ImageView加入到集合中
            viewList.add(imageView);
        }
        //View集合初始化好后，设置Adapter
        vp.setAdapter(new GuidePageAdapter(viewList));
        //设置滑动监听
        vp.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * 滑动后的监听
     * @param position
     */
    @Override
    public void onPageSelected(int position) {
        //循环设置当前页的标记图
        int length = imageIdArray.length;
        for (int i = 0;i<length;i++){
            ivPointArray[position].setBackgroundResource(R.drawable.point_focus);
            if (position != i){
                ivPointArray[i].setBackgroundResource(R.drawable.point_normal);
            }
        }

        //判断是否是最后一页，若是则显示按钮
        if (position == imageIdArray.length - 1){
            tv_start.setVisibility(View.VISIBLE);
        }else {
            tv_start.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
