package com.hevttc.jdr.interiew.view.customview;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;


public class Gradient extends RelativeLayout {
    private List<ImageView> imageViews;
    private List<Animation> outAnim;//淡出动画
    private List<Animation> inAnim;//淡入动画
    private Context mContext;

    private int couot;
    private int currentIndex;//当前的页面
    private LinearLayout linearLayout;
    private long time=6000;//动画间隔时间
    public Animation zoomOutAwayAnim;
    public Animation zoomOutNearAnim;
    private int size;
    private Run run;
    private Handler handler;

    public Gradient(Context context) {
        this(context, null);
    }

    public Gradient(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        if (handler!=null){
            handler.removeCallbacks(run);
            handler=null;
        }
        handler = new Handler(Looper.getMainLooper());

    }
    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 设置图片
     * @param imageViews
     */
    public void setImageViews(List<ImageView> imageViews) {
        this.imageViews = imageViews;
        for (int i = 0; i < imageViews.size(); i++) {
            imageViews.get(i).setScaleType(ImageView.ScaleType.FIT_XY);
            LayoutParams layoutParams = new LayoutParams(-1,-1);
            addView(imageViews.get(i),layoutParams);
        }
        //setonClick();
        //cratePoint();
        createAnim();
        start();
    }

    /**
     * 开启动画
     */
    private void start() {
        run = new Run();
        size = imageViews.size();
        handler.post(run);
    }

    /**
     * 创建动画
     */
    private void createAnim() {
           outAnim = new ArrayList<>();
           inAnim = new ArrayList<>();

        for (int i = 0; i < imageViews.size(); i++) {
                zoomOutAwayAnim = createZoomOutAwayAnim();
                zoomOutAwayAnim.setFillAfter(true);
                outAnim.add(zoomOutAwayAnim);

                zoomOutNearAnim = createZoomOutNearAnim();
                zoomOutNearAnim.setFillAfter(true);
                inAnim.add(zoomOutNearAnim);

        }
    }

    /**
     * 设置动画播放和handler延迟时间
     * @param time
     */
    public void setTime(long time) {
        this.time = time;
    }

    /** 创建一个淡出缩小的动画 */
    public Animation createZoomOutAwayAnim() {
        AnimationSet ret;
        Animation anim = null;

        ret = new AnimationSet(false);
        // 创建一个淡出的动画
        anim = new AlphaAnimation(1f, 0.2f);
        anim.setDuration(time);
        anim.setInterpolator(new DecelerateInterpolator());
        ret.addAnimation(anim);
        // 创建一个缩小的动画
        /*anim = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(MEDIUM);
        anim.setInterpolator(new DecelerateInterpolator());
        ret.addAnimation(anim);*/
        return ret;
    }
    /** 创建一个淡入缩小的动画 */
    public Animation createZoomOutNearAnim() {
        AnimationSet ret;
        Animation anim;
        ret = new AnimationSet(false);
        // 创建一个淡入的动画
        anim = new AlphaAnimation(0.2f, 1f);
        anim.setDuration(time);
        anim.setInterpolator(new LinearInterpolator());
        ret.addAnimation(anim);
        // 创建一个缩小的动画
        /*anim = new ScaleAnimation(3, 1, 3, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(MEDIUM);
        anim.setInterpolator(new DecelerateInterpolator());
        ret.addAnimation(anim);*/
        return ret;
    }
    class Run implements Runnable {

        @Override
        public void run() {
            //handler.removeCallbacks(run);

            final int i = couot % size;
            //解决点击事件的冲突
            for (int j = 0; j < size; j++) {
                if (j == i) {
                    imageViews.get(i).setClickable(true);
                } else {
                    imageViews.get(i).setClickable(false);
                }
            }
            if (couot < size) {
                if (i == size - 1) {
                    ImageView imageView = imageViews.get(0);
                    imageView.startAnimation(outAnim.get(0));
                    ImageView imageView2 = imageViews.get(size - 1);
                    imageView2.startAnimation(inAnim.get(size - 1));
                } else {
                    //当前的淡出,下一张淡入
                    ImageView imageView = imageViews.get(size - 1 - i);
                    imageView.startAnimation(outAnim.get(size - 1 - i));
                }
            } else {
                if (i == size - 1) {
                    //当显示到最后一张的时候,要跳到第一张
                    ImageView imageView = imageViews.get(0);
                    imageView.startAnimation(outAnim.get(0));
                    ImageView imageView2 = imageViews.get(size - 1);
                    imageView2.startAnimation(inAnim.get(size - 1));
                } else {
                    //当前的淡出,下一张淡入
                    ImageView imageView = imageViews.get(size - 1 - i);
                    imageView.startAnimation(outAnim.get(size - 1 - i));
                    ImageView imageView2 = imageViews.get(size - 2 - i);
                    imageView2.startAnimation(inAnim.get(size - 2 - i));
                }
            }
            currentIndex = i;
            // linearLayout.getChildAt(currentIndex % size).setEnabled(false);
            currentIndex++;
            // linearLayout.getChildAt(currentIndex % size).setEnabled(true);
            couot++;
            handler.postDelayed(this, time);
        }
    }
}
