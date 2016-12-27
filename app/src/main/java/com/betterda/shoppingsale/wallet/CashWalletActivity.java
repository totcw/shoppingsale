package com.betterda.shoppingsale.wallet;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.mylibrary.LoadingPager;
import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BaseActivity;
import com.betterda.shoppingsale.utils.UiUtils;
import com.betterda.shoppingsale.wallet.contract.CashWalletContract;
import com.betterda.shoppingsale.wallet.presenter.CashWalletPresenterImpl;
import com.betterda.shoppingsale.widget.NormalTopBar;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * 现金钱包
 * Created by Administrator on 2016/12/20.
 */

public class CashWalletActivity extends BaseActivity<CashWalletContract.Presenter> implements CashWalletContract.View {
    @BindView(R.id.topbar_cashwallet)
    public NormalTopBar mTopbarCashwallet;
    @BindView(R.id.tv_wallet2_money)
    public TextView mTvWallet2Money;
    @BindView(R.id.relative_wallet2_chongzhi)
    public RelativeLayout mRelativeWallet2Chongzhi;
    @BindView(R.id.loadpager_cashwallet)
    public LoadingPager mLoadpagerCashwallet;

    @Override
    protected CashWalletContract.Presenter onLoadPresenter() {
        return new CashWalletPresenterImpl();
    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_cashwallet);
    }

    @Override
    public void init() {
        super.init();
        mTopbarCashwallet.setTitle("消费钱包");
        mTopbarCashwallet.setActionText("明细");
        mTopbarCashwallet.setActionTextVisibility(true);
    }

    @OnClick({R.id.relative_wallet2_chongzhi, R.id.bar_back, R.id.bar_action})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bar_back:
                back();
                break;
            case R.id.bar_action:
                UiUtils.startIntent(getmActivity(),MingXiActivity.class);
                break;
            case R.id.relative_wallet2_chongzhi://提现
                break;
        }

    }
}
