package com.hevttc.jdr.interiew.view.fragment.choose_fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.bean.ExerciseQuestionBean;
import com.hevttc.jdr.interiew.util.StatusBarUtil;
import com.hevttc.jdr.interiew.view.activity.ExerciseTestActivity;
import com.hevttc.jdr.interiew.view.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hegeyang on 2018/4/12.
 */

@SuppressLint("ValidFragment")
public class SingleChooseFragment extends BaseFragment {
    private final ExerciseQuestionBean bean;
    private final int intTemp;
    @BindView(R.id.tv_single_choose_title)
    TextView tvSingleChooseTitle;
    @BindView(R.id.tv_single_choose_now)
    TextView tvSingleChooseNow;
    @BindView(R.id.tv_single_choose_all)
    TextView tvSingleChooseAll;
    @BindView(R.id.tv_single_choose_content)
    TextView tvSingleChooseContent;
    Unbinder unbinder;
    @BindView(R.id.rb_a)
    RadioButton rbA;
    @BindView(R.id.rb_b)
    RadioButton rbB;
    @BindView(R.id.rb_c)
    RadioButton rbC;
    @BindView(R.id.rb_d)
    RadioButton rbD;
    @BindView(R.id.rg_single)
    RadioGroup rgSingle;
    private ExerciseTestActivity topActivity;

    @SuppressLint("ValidFragment")
    public SingleChooseFragment(ExerciseQuestionBean bean, int intTemp) {
        this.bean = bean;
        this.intTemp = intTemp;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_single_choose;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initViews(View view) {
        tvSingleChooseContent.setText(bean.getQuestion());
        topActivity = (ExerciseTestActivity) this.mActivity;
        rbA.setText(bean.getA());
        rbB.setText(bean.getB());
        rbC.setText(bean.getC());
        rbD.setText(bean.getD());
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

    @Override
    public void onResume() {
        super.onResume();
        tvSingleChooseNow.setText( intTemp+ "");
        tvSingleChooseAll.setText(topActivity.getItemCout() + "");
    }
    public String getChooseItem(){
        String temp = "";
        if (rbA!=null)
        switch (rgSingle.getCheckedRadioButtonId()){
            case R.id.rb_a:
                temp = "1";
                break;
            case R.id.rb_b:
                temp = "2";
                break;
            case R.id.rb_c:
                temp = "3";
                break;
            case R.id.rb_d:
                temp = "4";
                break;
            default:
                temp = "";
                break;
        }
        return temp;

    }
}
