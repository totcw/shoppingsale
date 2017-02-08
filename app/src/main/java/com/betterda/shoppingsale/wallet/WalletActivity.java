package com.betterda.shoppingsale.wallet;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.betterda.mylibrary.LoadingPager;
import com.betterda.shoppingsale.R;
import com.betterda.shoppingsale.base.BaseActivity;
import com.betterda.shoppingsale.javabean.Wallet;
import com.betterda.shoppingsale.utils.UiUtils;
import com.betterda.shoppingsale.wallet.contract.WalletContract;
import com.betterda.shoppingsale.wallet.presenter.WalletPresenterImpl;
import com.betterda.shoppingsale.widget.NormalTopBar;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的钱包
 * Created by Administrator on 2016/12/20.
 */

public class WalletActivity extends BaseActivity<WalletContract.Presenter> implements WalletContract.View {
    @BindView(R.id.topbar_wallet)
    NormalTopBar mTopbarWallet;
    @BindView(R.id.tv_wallet_jinbi)
    TextView mTvCashWallet; //现金钱包
    @BindView(R.id.tv_wallet_yinbi)
    TextView mTvXiaoFeiWallet;
    @BindView(R.id.loadpager_wallet)
    LoadingPager mLoadpagerWallet;

    private String cashWallet;//现金钱包
    private String consumptionWallet;//消费钱包

    @Override
    protected WalletContract.Presenter onLoadPresenter() {
        return new WalletPresenterImpl();
    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_wallet);
    }

    @Override
    public void init() {
        super.init();
        mTopbarWallet.setTitle("我的钱包");
    }

    @OnClick({R.id.relative_wallet_jinbi, R.id.relative_wallet_yinbi,R.id.bar_back,R.id.relative_wallet_yinhangka})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relative_wallet_jinbi://现金钱包
                Intent intent = new Intent(getmActivity(), CashWalletActivity.class);
                intent.putExtra("money", cashWallet);
                UiUtils.startIntent(getmActivity(),intent);
                break;
            case R.id.relative_wallet_yinbi://消费钱包
                Intent intent2 = new Intent(getmActivity(), XiaoFeiWalletActivity.class);
                intent2.putExtra("money", consumptionWallet);
                UiUtils.startIntent(getmActivity(),intent2);
                break;
            case R.id.relative_wallet_yinhangka://我的银行卡
                UiUtils.startIntent(getmActivity(),MyYinHangKaActivity.class);
                break;
            case R.id.bar_back:
                back();
                break;
        }
    }

    @Override
    public LoadingPager getLodapger() {
        return mLoadpagerWallet;
    }

    @Override
    public void setValue(Wallet data) {
        cashWallet = data.getCashWallet();
        consumptionWallet = data.getConsumptionWallet();
        mTvCashWallet.setText(data.getCashWallet()+"元");
        mTvXiaoFeiWallet.setText(data.getConsumptionWallet()+"元");
    }
}
