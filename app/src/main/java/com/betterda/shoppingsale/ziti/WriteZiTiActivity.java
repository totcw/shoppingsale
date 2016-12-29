package com.betterda.shoppingsale.ziti;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BaseActivity;
import com.betterda.shoppingsale.base.IPresenter;
import com.betterda.shoppingsale.widget.NormalTopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/29.
 */

public class WriteZiTiActivity extends BaseActivity {
    @BindView(R.id.topbar_writezitima)
    NormalTopBar mTopbarWritezitima;
    @BindView(R.id.et_wiritezitima_number)
    EditText mEtWiritezitimaNumber;

    @Override
    protected IPresenter onLoadPresenter() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_writezitima);
    }

    @Override
    public void init() {
        super.init();
        mTopbarWritezitima.setTitle("自提码核对");
    }

    @OnClick({R.id.bar_back, R.id.btn_writezitima_comfirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bar_back:
                back();
                break;
            case R.id.btn_writezitima_comfirm:
                break;
        }
    }
}
