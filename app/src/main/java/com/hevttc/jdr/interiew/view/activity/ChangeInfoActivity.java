package com.hevttc.jdr.interiew.view.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hevttc.jdr.interiew.R;
import com.hevttc.jdr.interiew.adapter.UnivisityAdapter;
import com.hevttc.jdr.interiew.bean.BaseBean;
import com.hevttc.jdr.interiew.bean.UnivisityBean;
import com.hevttc.jdr.interiew.bean.UserInfoBean;
import com.hevttc.jdr.interiew.util.CircleTransform;
import com.hevttc.jdr.interiew.util.Constants;
import com.hevttc.jdr.interiew.util.OssUtils;
import com.hevttc.jdr.interiew.util.SPUtils;
import com.hevttc.jdr.interiew.util.StatusBarUtil;
import com.hevttc.jdr.interiew.util.TitleBuilder;
import com.hevttc.jdr.interiew.view.customview.ActionSheetDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    private static final String IMAGE_FILE_NAME = "temp_head_image.png";

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    String headUrl = Constants.SERVER_PHOTO_HEAD + "headimg/" + cropfile.getName();
                    update("avastar",headUrl);
                    break;
                case 1:
                    Toast.makeText(mContext, "上传失败，请稍后重试", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private android.app.AlertDialog dialog;
    private String bDate;
    private ListPopupWindow listPopupWindow;
    private ArrayAdapter<String> mAdapter;

    private void update(String key, String headUrl) {
        UserInfoBean signInfo = SPUtils.getSignInfo(mContext);
        OkGo.<String>get(Constants.API_CHANGE_INFO)
                .params("uid",signInfo.getId())
                .params(key,headUrl)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Type type = new TypeToken<BaseBean<UserInfoBean>>() {
                        }.getType();
                        BaseBean<UserInfoBean> baseBean = new Gson().fromJson(response.body(), type);
                        if (baseBean.isSuccess()){
                            SPUtils.saveString(mContext,Constants.SP_LOGIN,response.body());
                        }
                    }
                });

    }

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
                setBirtyday();
                if (dialog != null) {
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                Date parse = dateFormat.parse(bDate);
                                Date date = new Date();
                                int temp = parse.compareTo(date);
                                if (temp == 1) {
                                    Toast.makeText(mContext, "出生年月不能大于今天", Toast.LENGTH_SHORT).show();
                                } else {
                                    tvChangeBirth.setText(bDate);
                                    update("birth",bDate);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                break;
            case R.id.rl_change_education:
                change_education();
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
                        })
                        .show();
                break;
            case R.id.rl_change_nick:
                change_nick();
                break;
            case R.id.rl_change_sex:
                change_sex();
                break;
        }
    }

    private void change_education() {
        //final EditText et = new EditText(mContext);
        View inflate = View.inflate(mContext, R.layout.dialog_change_education, null);
        final EditText et_education = inflate.findViewById(R.id.et_education);
        final RecyclerView rcy_education = inflate.findViewById(R.id.rcy_education);
        rcy_education.setLayoutManager(new LinearLayoutManager(mContext));
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("毕业院校")
                .setView(inflate);
       builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String input = et_education.getText().toString();
                if (input.equals("")) {
                    Toast.makeText(getApplicationContext(), "院校不能为空" + input, Toast.LENGTH_LONG).show();
                }
                else {
                    tvChangeEducation.setText(input);
                    update("education",input);
                }
            }

        })
                .setNegativeButton("取消", null)
                .show();

        et_education.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if(!TextUtils.isEmpty(et_education.getText().toString())){
                            String trim = et_education.getText().toString().trim();
                            OkGo.<String>get(Constants.API_GET_UNIVISITY)
                                    .params("name",trim)
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(Response<String> response) {
                                            Type type = new TypeToken<ArrayList<UnivisityBean>>() {
                                            }.getType();
                                            final ArrayList<UnivisityBean> dataList = new Gson().fromJson(response.body(), type);
                                            if (dataList.size()!=0) {
                                                UnivisityAdapter univisityAdapter = new UnivisityAdapter(R.layout.item_univisity_dialog, dataList);
                                                rcy_education.setVisibility(View.VISIBLE);
                                                rcy_education.setAdapter(univisityAdapter);
                                                univisityAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                                    @Override
                                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                                        et_education.setText(dataList.get(position).getName());
                                                        rcy_education.setVisibility(View.GONE);
                                                    }
                                                });
                                            }
                                        }
                                    });
                        }else{
                            rcy_education.setVisibility(View.GONE);
                        }
                    }
                });
    }





    private void change_nick() {
        final EditText et = new EditText(mContext);

        new AlertDialog.Builder(this).setTitle("昵称")
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String input = et.getText().toString();
                        if (input.equals("")) {
                            Toast.makeText(getApplicationContext(), "昵称不能为空" + input, Toast.LENGTH_LONG).show();
                        }
                        else {
                            tvChangeNick.setText(input);
                            update("nickName",input);
                        }
                    }

                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void setBirtyday() {
        View v = LayoutInflater.from(this).inflate(R.layout.time_picker, null);
        dialog = new android.app.AlertDialog.
                Builder(this).create();


        final DatePicker datePicker = v.findViewById(R.id.datePicker);
        TextView tv_picker_finish = v.findViewById(R.id.tv_picker_finish);

        //win.addContentView(v,lp);
        tv_picker_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();
                bDate = year + "-" + (month >= 9 ? month + 1 : "0" + (month + 1)) + "-" + (day > 9 ? day : "0" + day);
                dialog.dismiss();
            }
        });
        dialog.setView(v);
        dialog.setCancelable(false);
        dialog.show();
    }

    private void change_sex() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext); //定义一个AlertDialog
        final String[] strarr = {"男","女"};
        builder.setItems(strarr, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface arg0, int arg1)
            {
                update("sex",strarr[arg1]);
                tvChangeSex.setText(strarr[arg1]);
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
            /*mCameraFilePath = path + "/" + System.currentTimeMillis() + ".jpg";

            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,Uri.parse(mCameraFilePath));*/
            File cameraDataDir = new File(path);
            if (!cameraDataDir.exists()){
                cameraDataDir.mkdirs();
            }
            mCameraFilePath = cameraDataDir.getAbsolutePath() + File.separator
                    + System.currentTimeMillis() + ".jpg";
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(new File(mCameraFilePath)));
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
                /*Bitmap bitmap = BitmapFactory.decodeFile(cropfile.getPath());
                ivChangeHead.setImageBitmap(bitmap);*/
                Picasso.with(mContext).load(Uri.fromFile(cropfile))
                        .transform(new CircleTransform())
                        .into(ivChangeHead);
                //submitHeadIcon();
                OssUtils.initOss(mContext).asyncPutObject(OssUtils.putImage(cropfile, "headimg/"), new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                    @Override
                    public void onSuccess(PutObjectRequest putObjectRequest, PutObjectResult putObjectResult) {
                       Message obtain = Message.obtain();
                        obtain.obj = putObjectResult;
                        obtain.what = 0;
                        handler.sendMessage(obtain);
                    }

                    @Override
                    public void onFailure(PutObjectRequest putObjectRequest, ClientException clientExcepion, ServiceException serviceException) {

                        // 请求异常
                        if (clientExcepion != null) {
                            // 本地异常如网络异常等
                            handler.sendEmptyMessage(1);
                            clientExcepion.printStackTrace();
                        }
                        if (serviceException != null) {
                            // 服务异常
                            handler.sendEmptyMessage(1);
                            Log.e("ErrorCode", serviceException.getErrorCode());
                            Log.e("RequestId", serviceException.getRequestId());
                            Log.e("HostId", serviceException.getHostId());
                            Log.e("RawMessage", serviceException.getRawMessage());
                        }
                    }
                });

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
