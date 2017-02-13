package com.betterda.shoppingsale.register;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.betterda.shoppingsale.BuildConfig;
import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BaseActivity;
import com.betterda.shoppingsale.http.MyObserver;
import com.betterda.shoppingsale.http.NetWork;
import com.betterda.shoppingsale.javabean.BaseCallModel;
import com.betterda.shoppingsale.register.contract.RegisterContract;
import com.betterda.shoppingsale.register.presenter.RegisterPresenterImpl;
import com.betterda.shoppingsale.utils.Constants;
import com.betterda.shoppingsale.utils.ImageTools;
import com.betterda.shoppingsale.utils.NetworkUtils;
import com.betterda.shoppingsale.utils.UiUtils;
import com.betterda.shoppingsale.widget.NormalTopBar;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kankan.wheel.widget.WheelDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 注册
 * Created by Administrator on 2016/12/29.
 */

public class RegisterActivity extends BaseActivity<RegisterContract.Presenter> implements RegisterContract.View {
    @BindView(R.id.topbar_register)
    NormalTopBar mTopbarRegister;
    @BindView(R.id.et_register_shop_name)
    EditText mEtRegisterShopName;//注册名称
    @BindView(R.id.et_register_name)
    EditText mEtRegisterName;//法人姓名
    @BindView(R.id.et_register_shop_number)
    EditText mEtRegisterShopNumber;//联系方式
    @BindView(R.id.tv_register_address1)
    TextView mTvRegisterAddress1;//所在区域
    @BindView(R.id.et_register_address2)
    EditText mEtRegisterAddress2;//详细地址
    @BindView(R.id.iv_reigster1)
    ImageView mIvReigster1;
    @BindView(R.id.sv_publish_tuwen2)
    ImageView mIvPublishTuwen2;
    @BindView(R.id.sv_publish_tuwen3)
    ImageView mIvvPublishTuwen3;
    private StringBuilder stringBuilder;
    private String goosimg;
    private int number = -1;//用来判断 图片选择

    @Override
    protected RegisterContract.Presenter onLoadPresenter() {
        return new RegisterPresenterImpl();
    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_reisgiter);
    }

    @Override
    public void init() {
        super.init();
        mTopbarRegister.setTitle("注册");

    }

    @OnClick({R.id.tv_register_address1, R.id.iv_reigster1, R.id.sv_publish_tuwen2,
            R.id.sv_publish_tuwen3, R.id.btn_register_commit,R.id.bar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register_address1://选择所在区域
                choseAddress();
                break;
            case R.id.iv_reigster1:
                number = 0;
                showPhoto();
                break;
            case R.id.sv_publish_tuwen2:
                number = 1;
                showPhoto();
                break;
            case R.id.sv_publish_tuwen3:
                number = 2;
                showPhoto();
                break;
            case R.id.btn_register_commit:
                getPresenter().commit();
                break;
            case R.id.bar_back:
                back();
                break;
        }
    }


    /**
     * 选择区域
     */
    private void choseAddress() {
        WheelDialog wheelDialog = new WheelDialog(getmActivity());
        wheelDialog.setOnAddressCListener(new WheelDialog.OnAddressCListener() {
            @Override
            public void onClick(String s, String s1, String s2) {
                stringBuilder = new StringBuilder();
                if (!TextUtils.isEmpty(s)) {
                    stringBuilder.append(s);

                }
                if (!TextUtils.isEmpty(s1)) {
                    stringBuilder.append(s1);

                }
                if (!TextUtils.isEmpty(s2)) {
                    stringBuilder.append(s2);

                }
                mTvRegisterAddress1.setText(stringBuilder.toString());
                getPresenter().setAddress(s, s1, s2);
            }
        });
        wheelDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        result(requestCode, resultCode, data);


    }
    /**
     * 开启头像选择
     */
    private void showPhoto() {
        View view = LayoutInflater.from(this).inflate(R.layout.pp_choose_photo,
                null);
        TextView tv_cameral = (TextView) view.findViewById(R.id.tv_photo_cameral);
        TextView tv_photo = (TextView) view.findViewById(R.id.tv_photo_photo);
        TextView tv_cancell = (TextView) view.findViewById(R.id.tv_photo_cancell);
        tv_cameral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageTools.paizhao(getmActivity(), Constants.PHOTOHRAPH);
                closePopupWindow();
            }
        });
        tv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageTools.choosePicture(getmActivity(), Constants.PHOTOZOOM);
                closePopupWindow();
            }
        });
        tv_cancell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closePopupWindow();
            }
        });
        setUpPopupWindow(view);
    }

    /**
     * 解析拍照或者图片库的图片
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private void result(int requestCode, int resultCode, Intent data) {
        resultSuccess(requestCode, resultCode, data);

        if (requestCode == 5 && resultCode == -1) { //resulrcode表示裁剪成功
            if (!ImageTools.checkSDCardAvailable()) {
                UiUtils.showToast(this, "内存卡错误,请检查您的内存卡");
                return;
            }
            // 防止内存溢出
            String path = Environment.getExternalStorageDirectory()
                    + "/image.png";

            Bitmap pic = ImageTools.scacleToBitmap(path, this);
            if (pic != null) {// 这个ImageView是拍照完成后显示图片

                setPhoto(pic);
            }
        }
    }

    private void resultSuccess(int requestCode, int resultCode, Intent data) {
        // 拍照
        if (requestCode == Constants.PHOTOHRAPH) {
            if (resultCode == RESULT_OK) {// 返回成功的时候
                ImageTools.cropImg(Constants.imageUri, this, 1, 1, 256, 256);
            } else if (resultCode == RESULT_CANCELED) {// 取消的时候
                UiUtils.showToast(this, "取消拍照");
            } else {
                // 失败的时候
                UiUtils.showToast(this, "拍照失败");
            }

        }
        // 读取相册缩放图片
        if (requestCode == Constants.PHOTOZOOM) {
            if (data != null) {
                Uri uri = data.getData();
                if (uri != null) {

                    ImageTools.cropImg(uri, this, 1, 1, 256, 256);

                } else {
                    UiUtils.showToast(this, "图片选取失败");
                }
            }

        }
    }

    //设置照片
    private void setPhoto(final Bitmap pic) {

        // 将bitmap保存到本地
        ImageTools.savePhotoToSDCard(pic, Constants.PHOTOPATH,
                Constants.PHOTONAME);
        // 保存照片地址
        goosimg =  Constants.PHOTOPATH + Constants.PHOTONAME
                + ".png";

        // 上传照片到服务器
        NetworkUtils.isNetWork(getmActivity(), mEtRegisterAddress2, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                //封装普通的string字段
                RequestBody account = RequestBody.create(MediaType.parse("text/plain"), "");
                RequestBody token = RequestBody.create(MediaType.parse("text/plain"), "");
                RequestBody merchant = RequestBody.create(MediaType.parse("text/plain"), "merchant");
                //封装文件
                RequestBody file = RequestBody.create(MediaType.parse("multipart/form-data"), new File(goosimg));

                //第一个参数是key,第二是文件名,如果没有文件名不会被当成文件
                MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", Constants.PHOTONAME
                        + ".png", file);

                getRxManager().add(NetWork.getNetService()
                        .getImgUpload(account,token,merchant,filePart)
                        .compose(NetWork.handleResult(new BaseCallModel<String>()))
                        .subscribe(new MyObserver<String>() {
                            @Override
                            protected void onSuccess(String data, String resultMsg) {
                                if (BuildConfig.LOG_DEBUG) {
                                    System.out.println("图片上传路径success:"+data.toString());
                                }
                                getPresenter().setimageUrl(number, data);
                                if (number == 0) {
                                    mIvReigster1.setImageBitmap(pic);
                                } else if (number == 1) {
                                    mIvPublishTuwen2.setImageBitmap(pic);
                                } else if (number == 2) {
                                    mIvvPublishTuwen3.setImageBitmap(pic);
                                }
                            }


                            @Override
                            public void onFail(String resultMsg) {
                                if (BuildConfig.LOG_DEBUG) {
                                    System.out.println("图片上传路径fail:"+resultMsg);
                                }
                            }

                            @Override
                            public void onExit() {

                            }
                        }));
            }
        });

    }


    @Override
    public void dismiss() {
        super.dismiss();
        UiUtils.backgroundAlpha(1.0f,getmActivity());
    }

    @Override
    public View getTextview() {
        return mEtRegisterAddress2;
    }

    @Override
    public EditText getName() {
        return mEtRegisterShopName;
    }

    @Override
    public EditText getNumber() {
        return mEtRegisterShopNumber;
    }

    @Override
    public EditText getAddressDetail() {
        return mEtRegisterAddress2;
    }

    @Override
    public EditText getLeaglPerson() {
        return mEtRegisterName;
    }
}
