package com.betterda.shoppingsale.register;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BaseActivity;
import com.betterda.shoppingsale.register.contract.RegisterContract;
import com.betterda.shoppingsale.register.presenter.RegisterPresenterImpl;
import com.betterda.shoppingsale.widget.NormalTopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kankan.wheel.widget.WheelDialog;

/**
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
                break;
            case R.id.sv_publish_tuwen2:
                break;
            case R.id.sv_publish_tuwen3:
                break;
            case R.id.btn_register_commit:
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
            }
        });
        wheelDialog.show();
    }
}
