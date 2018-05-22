package com.hevttc.jdr.interiew.view.fragment.choose_fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.bean.ExerciseQuestionBean;
import com.hevttc.jdr.interiew.view.activity.ExerciseTestActivity;
import com.hevttc.jdr.interiew.view.fragment.BaseFragment;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hegeyang on 2018/4/12.
 */

@SuppressLint("ValidFragment")
public class MoreChooseFragment extends BaseFragment {
    private final ExerciseQuestionBean bean;
    private final int intTemp;
    @BindView(R.id.tv_double_choose_title)
    TextView tvDoubleChooseTitle;
    @BindView(R.id.tv_double_choose_now)
    TextView tvDoubleChooseNow;
    @BindView(R.id.tv_double_choose_all)
    TextView tvDoubleChooseAll;
    @BindView(R.id.tv_double_choose_content)
    TextView tvDoubleChooseContent;
    @BindView(R.id.cb_a)
    CheckBox cbA;
    @BindView(R.id.cb_b)
    CheckBox cbB;
    @BindView(R.id.cb_c)
    CheckBox cbC;
    @BindView(R.id.cb_d)
    CheckBox cbD;
    Unbinder unbinder;
    private ExerciseTestActivity topActivity;

    @SuppressLint("ValidFragment")
    public MoreChooseFragment(ExerciseQuestionBean bean, int intTemp) {
        this.bean = bean;
        this.intTemp = intTemp;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_double_choose;
    }

    @Override
    public void initDatas() {
        tvDoubleChooseContent.setText(bean.getQuestion());
        topActivity = (ExerciseTestActivity) this.mActivity;
        cbA.setText(bean.getA());
        cbB.setText(bean.getB());
        cbC.setText(bean.getC());
        cbD.setText(bean.getD());
    }

    @Override
    public void initViews(View view) {

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
        tvDoubleChooseNow.setText(intTemp + "");
        tvDoubleChooseAll.setText(topActivity.getItemCout() + "");
    }
    public String getChooseItem(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        if(cbA!=null) {
            if (cbA.isChecked()) {
                stringBuilder.append("1,");
            }
            if (cbB.isChecked()) {
                stringBuilder.append("2,");
            }
            if (cbC.isChecked()) {
                stringBuilder.append("3,");
            }
            if (cbD.isChecked()) {
                stringBuilder.append("4,");
            }
        }
        String s = stringBuilder.toString();
        if (TextUtils.isEmpty(s))
            return "";
        else
            return s.substring(0,s.length()-1);
    }
}
