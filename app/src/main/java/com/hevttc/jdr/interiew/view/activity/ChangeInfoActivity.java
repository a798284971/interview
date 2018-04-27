package com.hevttc.jdr.interiew.view.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.bean.UserInfoBean;
import com.hevttc.jdr.interiew.util.CircleTransform;
import com.hevttc.jdr.interiew.util.SPUtils;
import com.hevttc.jdr.interiew.util.StatusBarUtil;
import com.hevttc.jdr.interiew.util.TitleBuilder;
import com.hevttc.jdr.interiew.view.customview.ActionSheetDialog;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hegeyang on 2018/4/27.
 */

public class ChangeInfoActivity extends BaseActivity implements View.OnClickListener {
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
    @BindView(R.id.iv_change_head)
    ImageView ivChangeHead;
    @BindView(R.id.rl_change_head)
    RelativeLayout rlChangeHead;
    @BindView(R.id.tv_change_nick)
    TextView tvChangeNick;
    @BindView(R.id.rl_change_nick)
    RelativeLayout rlChangeNick;
    @BindView(R.id.tv_change_sex)
    TextView tvChangeSex;
    @BindView(R.id.rl_change_sex)
    RelativeLayout rlChangeSex;
    @BindView(R.id.tv_change_birth)
    TextView tvChangeBirth;
    @BindView(R.id.rl_change_birth)
    RelativeLayout rlChangeBirth;
    @BindView(R.id.tv_change_education)
    TextView tvChangeEducation;
    @BindView(R.id.rl_change_education)
    RelativeLayout rlChangeEducation;
    private String path = Environment.getExternalStorageDirectory().getPath() + File.separator + "interview" + File.separator + "icon";
    private File cropfile;
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static int output_X = 250;
    private static int output_Y = 250;
    private static final int REQUEST_CAMERA = 0;//动态请求权限的返回标识
    private static String[] PERMISSIONS_CEMERA = {Manifest.permission.CAMERA,
            Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_EXTERNAL_STORAGE};
    private String mCameraFilePath;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_info;
    }

    @Override
    protected void initViews() {

        new TitleBuilder(this).setLeftIco(R.mipmap.row_back)
                .setLeftIcoListening(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).setTitleText("个人信息");
        StatusBarUtil.setViewTopPadding(ChangeInfoActivity.this,R.id.top_bar);
        UserInfoBean signInfo = SPUtils.getSignInfo(mContext);
        if(!TextUtils.isEmpty(signInfo.getNickname()))
            tvChangeNick.setText(signInfo.getNickname());
        else
            tvChangeNick.setText("请设置昵称");
        if (!TextUtils.isEmpty(signInfo.getEducation()))
            tvChangeEducation.setText(signInfo.getEducation());
        else
            tvChangeEducation.setText("暂未设置学校");
        if (!TextUtils.isEmpty(signInfo.getAvastar()))
            Picasso.with(mContext).load(signInfo.getAvastar())
                    .transform(new CircleTransform())
                    .into(ivChangeHead);
        else
            Picasso.with(mContext).load(R.mipmap.ic_head_default)
                    .transform(new CircleTransform())
                    .into(ivChangeHead);
        if (!TextUtils.isEmpty(signInfo.getSex()))
            tvChangeSex.setText(signInfo.getSex());
        else
            tvChangeSex.setText("男");
        if (!TextUtils.isEmpty(signInfo.getBirth()))
            tvChangeBirth.setText(signInfo.getBirth());
        else
            tvChangeBirth.setText("请设置出生年月");
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void initListeners() {
        rlChangeBirth.setOnClickListener(this);
        rlChangeEducation.setOnClickListener(this);
        rlChangeHead.setOnClickListener(this);
        rlChangeNick.setOnClickListener(this);
        rlChangeSex.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_change_birth:

                break;
            case R.id.rl_change_education:

                break;
            case R.id.rl_change_head:
                new ActionSheetDialog(this)
                        .builder()
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .addSheetItem("相册选择", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                Intent intentFromGallery = new Intent();
                                // 设置文件类型
                                intentFromGallery.setType("image/*");
                                intentFromGallery.setAction(Intent.ACTION_PICK);
                                startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
                            }


                        })
                        .addSheetItem("相机", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    showSetPermission();
                                } else {
                                    showCamera();
                                }
                            }
                        }).show();
                break;
            case R.id.rl_change_nick:

                break;
            case R.id.rl_change_sex:
                change_sex();
                break;
        }
    }

    private void change_sex() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext); //定义一个AlertDialog
        String[] strarr = {"男","女"};
        builder.setItems(strarr, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface arg0, int arg1)
            {
                String sex = "2";
                // 自动生成的方法存根
                if (arg1 == 0) {//男
                    sex = "1";
                }else {//女
                    sex = "2";
                }

            }
        });
        builder.show();
    }

    private void showCamera() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 判断存储卡是否可用，存储照片文件
        if (hasSdcard()) {
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            mCameraFilePath = path + "/" + System.currentTimeMillis() + ".jpg";
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                    .fromFile(new File(mCameraFilePath)));
            startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showSetPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
                ||ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                ||ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)
                !=PackageManager.PERMISSION_GRANTED) {
            // 权限未被授予,请求用户获取权限
            this.requestPermissions(PERMISSIONS_CEMERA,REQUEST_CAMERA);
        } else {
            showCamera();
        }
    }
    public  boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // 用户没有进行有效的设置操作，返回
        if (resultCode == RESULT_CANCELED) {
            //Toast.makeText(Updata_meActivity.this, "取消了", Toast.LENGTH_LONG).show();
            return;
        }

        switch (requestCode) {
            case CODE_GALLERY_REQUEST:
                cropRawPhoto(intent.getData());
                break;

            case CODE_CAMERA_REQUEST:
                if (hasSdcard()) {
                    File file = new File(path);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    
                    cropRawPhoto(Uri.fromFile(new File(mCameraFilePath)));

                } else {
                    
                }

                break;

            case CODE_RESULT_REQUEST:
                //Bitmap bitmap = BitmapFactory.decodeFile(cropfile.getPath());
                //ivChangeHead.setImageBitmap(bitmap);
                Picasso.with(mContext).load(Uri.fromFile(cropfile))
                        .transform(new CircleTransform())
                        .into(ivChangeHead);
                //submitHeadIcon();
                break;
        }

        super.onActivityResult(requestCode, resultCode, intent);
    }

    private void cropRawPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", output_X);
        intent.putExtra("outputY", output_Y);
        intent.putExtra("return-data", true);
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        cropfile = new File(file.getPath(), System.currentTimeMillis() + ".png");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cropfile));
        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            //摄像头请求返回结果。
            case REQUEST_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showCamera();
                } else {
                    Toast.makeText(mContext,"权限获取失败，请手动获取", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
