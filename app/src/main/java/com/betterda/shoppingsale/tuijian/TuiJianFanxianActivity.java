package com.betterda.shoppingsale.tuijian;

import android.view.View;
import android.widget.TextView;

import com.betterda.mylibrary.LoadingPager;
import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BaseActivity;
import com.betterda.shoppingsale.base.IPresenter;
import com.betterda.shoppingsale.utils.UiUtils;
import com.betterda.shoppingsale.wallet.MingXiActivity;
import com.betterda.shoppingsale.widget.NormalTopBar;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * 推荐返现
 * Created by Administrator on 2016/12/20.
 */

public class TuiJianFanxianActivity extends BaseActivity {
    @BindView(R.id.topbar_cashwallet)
    NormalTopBar mTopbarCashwallet;
    @BindView(R.id.tv_wallet2_money)
    TextView mTvWallet2Money;
    @BindView(R.id.loadpager_cashwallet)
    LoadingPager mLoadpagerCashwallet;

    @Override
    protected IPresenter onLoadPresenter() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_tuijianfanxian);
    }

    @Override
    public void init() {
        super.init();
        mTopbarCashwallet.setTitle("会员返现");
        mTopbarCashwallet.setActionText("明细");
        mTopbarCashwallet.setActionTextVisibility(true);
    }

    @OnClick({R.id.bar_back,R.id.bar_action})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.bar_back:
                back();
                break;
            case R.id.bar_action:
                UiUtils.startIntent(getmActivity(), MingXiActivity.class);
                break;
        }
    }
}
