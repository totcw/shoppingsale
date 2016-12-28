package com.betterda.shoppingsale.setting;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BaseActivity;
import com.betterda.shoppingsale.setting.contract.SettingContract;
import com.betterda.shoppingsale.setting.presenter.SettingPresenterImpl;
import com.betterda.shoppingsale.widget.NormalTopBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置
 * Created by Administrator on 2016/12/19.
 */

public class SettingActivity extends BaseActivity<SettingContract.Presenter> implements SettingContract.View {
    @BindView(R.id.topbar_setting)
    NormalTopBar topbarSetting;
    @BindView(R.id.linear_setting_us)
    LinearLayout linearSettingUs;
    @BindView(R.id.btn_setting_exit)
    Button btnSettingExit;

    @Override
    protected SettingContract.Presenter onLoadPresenter() {
        return new SettingPresenterImpl();
    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_setting);
    }


    @Override
    public void init() {
        super.init();
        topbarSetting.setTitle("设置");
    }

    @OnClick({R.id.bar_back,  R.id.linear_setting_us, R.id.btn_setting_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_setting_us:
                break;
            case R.id.btn_setting_exit:
                break;
            case R.id.bar_back:
                back();
                break;
        }
    }
}
